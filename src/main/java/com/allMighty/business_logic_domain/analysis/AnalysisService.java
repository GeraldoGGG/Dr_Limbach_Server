package com.allMighty.business_logic_domain.analysis;

import static com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper.*;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;
import static com.example.jooq.generated.tables.Category.CATEGORY;

import com.allMighty.business_logic_domain.analysis.analysis_category.AnalysisCategoryRepository;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDTO;
import com.allMighty.business_logic_domain.analysis.dto.AnalysisDetailDTO;
import com.allMighty.business_logic_domain.analysis.mapper.AnalysisMapper;
import com.allMighty.business_logic_domain.export.ExportService;
import com.allMighty.business_logic_domain.general.EntityIdDTO;
import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.image.ImageService;
import com.allMighty.business_logic_domain.tag.TagRepository;
import com.allMighty.enitity.TagEntity;
import com.allMighty.enitity.analysis.AnalysisCategoryEntity;
import com.allMighty.enitity.analysis.AnalysisDetailEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import jakarta.persistence.EntityNotFoundException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AnalysisService extends BaseService {

  private final FilterParser<AnalysisField> filterParser =
      new FilterParser<>(AnalysisField.values());

  private final AnalysisRepository analysisRepository;
  private final TagRepository tagRepository;
  private final ImageService imageService;
  private final ExportService exportService;
  private final AnalysisCategoryRepository analysisCategoryRepository;

  public List<AnalysisDTO> getAnalyses(List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    List<AnalysisDTO> analysisDTOS =
        analysisRepository.getAllAnalyses(conditions, pageDescriptor).stream()
            .map(AnalysisMapper::toAnalysisDTO)
            .toList();

    addImages(analysisDTOS);

    return analysisDTOS;
  }

  public AnalysisDTO getAnalysisById(Long id) {
    AnalysisDTO analysisDTO =
        analysisRepository
            .findById(id)
            .map(AnalysisMapper::toAnalysisDTO)
            .orElseThrow(() -> new BadRequestException("Analysis not found!"));

    addImages(Collections.singletonList(analysisDTO));

    return analysisDTO;
  }

  public Long count() {
    return analysisRepository.count();
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return analysisRepository.count(conditions);
  }

  @Transactional
  public Long updateAnalysis(Long id, AnalysisDTO analysisDTO) {
    AnalysisEntity analysisEntity = em.find(AnalysisEntity.class, id);
    if (analysisEntity == null) {
      throw new BadRequestException("Analysis not found!");
    }
    toAnalysisEntity(analysisDTO, analysisEntity);

    // details
    updateDetailEntities(analysisDTO.getDetails(), analysisEntity);

    // tags
    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(analysisDTO.getTags(), em);
    analysisEntity.setTags(tagEntities);

    // category
    updateCategory(analysisDTO.getCategory(), analysisEntity);

    AnalysisEntity saved = em.merge(analysisEntity);

    List<ImageDTO> imageDTOs = analysisDTO.getImages();

    boolean hasImageData =
        imageDTOs.stream().map(ImageDTO::getImageData).anyMatch(StringUtils::isNotBlank);

    if (hasImageData) {
      imageService.deleteImages(Collections.singletonList(id), EntityType.ANALYSIS);
      imageService.createImages(imageDTOs, EntityType.ANALYSIS, id);
    }

    return saved.getId();
  }

  @Transactional
  public Long createAnalysis(AnalysisDTO analysisDTO) {
    AnalysisEntity analysisEntity = new AnalysisEntity();
    toAnalysisEntity(analysisDTO, analysisEntity);

    // details
    updateDetailEntities(analysisDTO.getDetails(), analysisEntity);

    // tags
    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(analysisDTO.getTags(), em);
    analysisEntity.setTags(tagEntities);

    // category
    updateCategory(analysisDTO.getCategory(), analysisEntity);

    AnalysisEntity saved = em.merge(analysisEntity);

    List<ImageDTO> images = analysisDTO.getImages();
    imageService.createImages(images, EntityType.ANALYSIS, saved.getId());

    return saved.getId();
  }

  private void updateCategory(EntityIdDTO category, AnalysisEntity analysisEntity) {
    if (category != null) {
      Long categoryId = category.getId();
      String categoryName = category.getName();

      // find by id
      if (categoryId != null) {
        AnalysisCategoryEntity analysisCategoryEntity =
            em.find(AnalysisCategoryEntity.class, categoryId);
        if (analysisCategoryEntity != null) {
          analysisEntity.setCategory(analysisCategoryEntity);
          return;
        }
      }

      // find by name
      if (StringUtils.isNotBlank(categoryName)) {
        List<AnalysisCategoryEntity> allAnalysisCategories =
            analysisCategoryRepository.getAllAnalysisCategories(CATEGORY.NAME.eq(categoryName));
        if (CollectionUtils.isNotEmpty(allAnalysisCategories)) {
          analysisEntity.setCategory(
              em.find(AnalysisCategoryEntity.class, allAnalysisCategories.get(0).getId()));
          return;
        }
      }
    }
    analysisEntity.setCategory(null);
  }

  private void addImages(List<AnalysisDTO> analysisDTOS) {
    List<Long> referenceIds = analysisDTOS.stream().map(AnalysisDTO::getId).toList();
    Map<Long, List<ImageDTO>> images = imageService.getImages(referenceIds, EntityType.ANALYSIS);
    analysisDTOS.forEach(dto -> dto.setImages(images.get(dto.getId())));
  }

  @Transactional
  public void createInitialAnalysis() {
    List<ExcelAnalysisDataDTO> dtoList = exportService.fetchAnalysisExcel();
    for (ExcelAnalysisDataDTO dto : dtoList) {
      String analiza = dto.getAnaliza();
      String sinonimi = dto.getSinonimi();
      String kategoria = dto.getKategoria();
      String akredituarNgaISO15189 = dto.getAkredituarNgaISO15189();

      // category
      AnalysisEntity analysisEntity = new AnalysisEntity();

      Condition nameCondition = CATEGORY.NAME.eq(kategoria);
      AnalysisCategoryEntity found =
          analysisCategoryRepository
              .getAllAnalysisCategories(Collections.singletonList(nameCondition))
              .stream()
              .findFirst()
              .orElse(null);
      AnalysisCategoryEntity analysisCategoryEntity;
      if (found == null) {
        analysisCategoryEntity = new AnalysisCategoryEntity();
        analysisCategoryEntity.setName(kategoria);
        em.persist(analysisCategoryEntity);
      } else {
        analysisCategoryEntity = em.find(AnalysisCategoryEntity.class, found.getId());
      }

      // analysis
      analysisEntity.setSynonym(sinonimi);
      analysisEntity.setMedicalName(analiza);
      boolean isIso = "Po".equals(akredituarNgaISO15189);
      analysisEntity.setIsoVerified(isIso);

      // details
      List<AnalysisDetailEntity> detailsList = new ArrayList<>();

      // Create AnalysisDetailEntity for each property in the DTO
      detailsList.add(createAnalysisDetailEntity("mostra", dto.getMostra(), analysisEntity));
      detailsList.add(
          createAnalysisDetailEntity("stabiliteti", dto.getStabiliteti(), analysisEntity));
      detailsList.add(
          createAnalysisDetailEntity("preanalitika", dto.getPreanalitika(), analysisEntity));
      detailsList.add(createAnalysisDetailEntity("metoda", dto.getMetoda(), analysisEntity));
      detailsList.add(
          createAnalysisDetailEntity(
              "indikacioniKlinik", dto.getIndikacioniKlinik(), analysisEntity));
      detailsList.add(
          createAnalysisDetailEntity(
              "interpretimiIRrezultatit", dto.getInterpretimiIRrezultatit(), analysisEntity));
      analysisEntity.setAnalysisDetailEntities(detailsList);

      analysisEntity.setCategory(analysisCategoryEntity);

      em.persist(analysisEntity);
    }
  }

  private AnalysisDetailEntity createAnalysisDetailEntity(
      String key, String value, AnalysisEntity analysisEntity) {
    AnalysisDetailEntity detail = new AnalysisDetailEntity();
    detail.setKey_value(key);
    detail.setString_value(value);
    detail.setAnalysis(analysisEntity);
    return detail;
  }

  public void updateDetailEntities(List<AnalysisDetailDTO> dtoList, AnalysisEntity entity) {
    if (entity.getAnalysisDetailEntities() != null) {
      entity.getAnalysisDetailEntities().clear();
    }

    if (CollectionUtils.isEmpty(dtoList)) {
      return;
    }

    for (AnalysisDetailDTO detailDTO : dtoList) {
      Long detailDTOId = detailDTO.getId();

      AnalysisDetailEntity analysisDetailEntity;
      if (detailDTOId != null) {
        analysisDetailEntity = em.find(AnalysisDetailEntity.class, detailDTOId);
        if (analysisDetailEntity == null) {
          throw new EntityNotFoundException("Detail with id " + detailDTO.getId() + " not found");
        }
      } else {
        analysisDetailEntity = new AnalysisDetailEntity();
      }
      analysisDetailEntity.setKey_value(detailDTO.getKeyValue());
      analysisDetailEntity.setString_value(detailDTO.getStringValue());
      entity.addAnalysisDetail(analysisDetailEntity);
    }
  }

  public List<EntityIdDTO> getSimpleAnalyses() {
    return analysisRepository.getSimpleAnalyses();
  }
}
