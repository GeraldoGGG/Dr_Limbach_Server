package com.allMighty.business_logic_domain.analysis.analysis_category;

import static com.allMighty.business_logic_domain.analysis.analysis_category.AnalysisCategoryMapper.toAnalysisCategoryEntity;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisCategoryDTO;
import com.allMighty.enitity.analysis.AnalysisCategoryEntity;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisCategoryService extends BaseService {

  private final AnalysisCategoryRepository analysisCategoryRepository;

  public List<AnalysisCategoryDTO> getAllAnalysisCategories() {
    return analysisCategoryRepository.getAllAnalysisCategories().stream()
        .map(AnalysisCategoryMapper::toAnalysisCategoryDTO)
        .toList();
  }

  public AnalysisCategoryDTO getAnalysisCategoryById(Long id) {
    return analysisCategoryRepository
        .getAnalysisCategoryById(id)
        .map(AnalysisCategoryMapper::toAnalysisCategoryDTO)
        .orElseThrow(() -> new BadRequestException("Category not found!"));
  }

  @Transactional
  public Long createAnalysisCategory(AnalysisCategoryDTO categoryDTO) {
    AnalysisCategoryEntity categoryEntity = new AnalysisCategoryEntity();
    toAnalysisCategoryEntity(categoryDTO, categoryEntity);

    List<Long> analysisIds = categoryDTO.getAnalysisIds();
    if (CollectionUtils.isNotEmpty(analysisIds)) {
      analysisIds.stream()
          .map(id -> em.find(AnalysisEntity.class, id))
          .filter(Objects::nonNull)
          .forEach(categoryEntity::addAnalysis);
    }

    em.persist(categoryEntity);
    return categoryEntity.getId();
  }

  @Transactional
  public Long updateAnalysisCategory(Long categoryId, AnalysisCategoryDTO categoryDTO) {
    AnalysisCategoryEntity categoryEntity = em.find(AnalysisCategoryEntity.class, categoryId);
    if (categoryEntity == null) {
      throw new EntityNotFoundException("Category not found with ID: " + categoryId);
    }

    toAnalysisCategoryEntity(categoryDTO, categoryEntity);

    updateCategoryAnalysis(categoryDTO, categoryEntity);

    return em.merge(categoryEntity).getId();
  }

  private void updateCategoryAnalysis(
      AnalysisCategoryDTO categoryDTO, AnalysisCategoryEntity categoryEntity) {

    List<Long> analysisIds =
        categoryDTO.getAnalysisIds() != null
            ? categoryDTO.getAnalysisIds()
            : Collections.emptyList();

    // find the new analyses
    List<AnalysisEntity> newAnalyses =
        analysisIds.stream()
            .map(id -> em.find(AnalysisEntity.class, id))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    // remove old
    categoryEntity.getAnalyses().forEach(analysis -> analysis.setCategory(null));

    // add new
    newAnalyses.forEach(newAnalysis -> newAnalysis.setCategory(categoryEntity));

    categoryEntity.setAnalyses(newAnalyses);
  }
}
