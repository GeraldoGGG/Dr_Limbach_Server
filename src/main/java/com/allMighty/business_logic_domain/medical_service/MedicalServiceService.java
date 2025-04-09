package com.allMighty.business_logic_domain.medical_service;

import static com.allMighty.business_logic_domain.medical_service.MedicalServiceMapper.*;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.image.ImageDTO;
import com.allMighty.business_logic_domain.image.ImageService;
import com.allMighty.business_logic_domain.tag.TagRepository;
import com.allMighty.enitity.ArticleEntity;
import com.allMighty.enitity.MedicalServiceEntity;
import com.allMighty.enitity.TagEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.enumeration.EntityType;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import com.allMighty.global_operation.response.page.PageDescriptor;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MedicalServiceService extends BaseService {

  private final FilterParser<MedicalServiceField> filterParser =
      new FilterParser<>(MedicalServiceField.values());

  private final MedicalServiceRepository medicalServiceRepository;
  private final TagRepository tagRepository;
  private final ImageService imageService;

  public List<MedicalServiceDTO> getMedicalServices(
      List<String> filters, PageDescriptor pageDescriptor) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    List<MedicalServiceDTO> dtos =
        medicalServiceRepository.getAllMedicalServices(conditions, pageDescriptor).stream()
            .map(MedicalServiceMapper::toMedicalServiceDTO)
            .toList();

    addImages(dtos);

    return dtos;
  }

  public MedicalServiceDTO getMedicalServiceById(Long id) {
    MedicalServiceDTO medicalServiceDTO =
        medicalServiceRepository
            .findById(id)
            .map(MedicalServiceMapper::toMedicalServiceDTO)
            .orElseThrow(() -> new BadRequestException("Medical service not found!"));

    addImages(Collections.singletonList(medicalServiceDTO));

    return medicalServiceDTO;
  }

  public Long count() {
    return medicalServiceRepository.count();
  }

  public Long count(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return medicalServiceRepository.count(conditions);
  }

  @Transactional
  public Long updateMedicalService(Long id, MedicalServiceDTO medicalServiceDTO) {
    MedicalServiceEntity medicalServiceEntity = em.find(MedicalServiceEntity.class, id);
    if (medicalServiceEntity == null) {
      throw new BadRequestException("Medical service not found!");
    }

    toMedicalServiceEntity(medicalServiceDTO, medicalServiceEntity);

    medicalServiceEntity.setArticles(new ArrayList<>());
    updateArticles(medicalServiceDTO.getArticleIds(), medicalServiceEntity);

    medicalServiceEntity.setAnalysis(new ArrayList<>());
    updateAnalysis(medicalServiceDTO.getAnalysisIds(), medicalServiceEntity);

    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(medicalServiceDTO.getTags(), em);
    medicalServiceEntity.setTags(tagEntities);

    MedicalServiceEntity saved = em.merge(medicalServiceEntity);

    List<ImageDTO> imageDTOs = medicalServiceDTO.getImages();

    boolean hasImageData =
        imageDTOs.stream().map(ImageDTO::getImageData).anyMatch(StringUtils::isNotBlank);

    if (hasImageData) {
      imageService.deleteImages(Collections.singletonList(id), EntityType.SERVICE);
      imageService.createImages(imageDTOs, EntityType.SERVICE, id);
    }
    return saved.getId();
  }

  @Transactional
  public Long createMedicalService(MedicalServiceDTO medicalServiceDTO) {
    MedicalServiceEntity entity = new MedicalServiceEntity();
    toMedicalServiceEntity(medicalServiceDTO, entity);

    updateArticles(medicalServiceDTO.getArticleIds(), entity);
    updateAnalysis(medicalServiceDTO.getAnalysisIds(), entity);

    Set<TagEntity> tagEntities = tagRepository.updateTagEntities(medicalServiceDTO.getTags(), em);
    entity.setTags(tagEntities);

    em.persist(entity);

    List<ImageDTO> images = medicalServiceDTO.getImages();
    imageService.createImages(images, EntityType.SERVICE, entity.getId());

    return entity.getId();
  }

  private void updateArticles(List<Long> articleIds, MedicalServiceEntity medicalServiceEntity) {
    if (CollectionUtils.isEmpty(articleIds)) {
      return;
    }
    for (Long id : articleIds) {
      if (id == null) {
        continue;
      }
      ArticleEntity article = em.find(ArticleEntity.class, id);
      if (article != null) {
        medicalServiceEntity.getArticles().add(article);
      }
    }
  }

  private void updateAnalysis(List<Long> analysisIds, MedicalServiceEntity medicalServiceEntity) {
    if (CollectionUtils.isEmpty(analysisIds)) {
      return;
    }

    for (Long id : analysisIds) {
      if (id == null) {
        continue;
      }
      AnalysisEntity analysis = em.find(AnalysisEntity.class, id);
      if (analysis != null) {
        medicalServiceEntity.getAnalysis().add(analysis);
      }
    }
  }

  private void addImages(List<MedicalServiceDTO> dtos) {
    List<Long> referenceIds = dtos.stream().map(MedicalServiceDTO::getId).toList();
    Map<Long, List<ImageDTO>> images = imageService.getImages(referenceIds, EntityType.SERVICE);
    dtos.forEach(dto -> dto.setImages(images.get(dto.getId())));
  }
}
