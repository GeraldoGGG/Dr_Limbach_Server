package com.allMighty.business_logic_domain.analysis.analysis_package;

import static com.allMighty.business_logic_domain.analysis.analysis_package.AnalysisPackageMapper.toAnalysisPackageEntity;
import static com.allMighty.global_operation.filter.JooqConditionBuilder.buildConditions;

import com.allMighty.business_logic_domain.analysis.dto.AnalysisPackageDTO;
import com.allMighty.enitity.analysis.AnalysisEntity;
import com.allMighty.enitity.analysis.AnalysisPackageEntity;
import com.allMighty.global_operation.BaseService;
import com.allMighty.global_operation.exception_management.exception.BadRequestException;
import com.allMighty.global_operation.filter.FilterParser;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jooq.Condition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisPackageService extends BaseService {

  private final FilterParser<PackageFields> filterParser =
      new FilterParser<>(PackageFields.values());

  private final AnalysisPackageRepository analysisPackageRepository;

  public List<AnalysisPackageDTO> getAllAnalysisPackages(List<String> filters) {
    List<Condition> conditions = buildConditions(filters, filterParser);
    return analysisPackageRepository.getAllAnalysisPackages(conditions).stream()
        .map(AnalysisPackageMapper::toAnalysisPackageDTO)
        .toList();
  }

  public AnalysisPackageDTO getAnalysisPackageById(Long id) {
    return analysisPackageRepository
        .getAnalysisPackageById(id)
        .map(AnalysisPackageMapper::toAnalysisPackageDTO)
        .orElseThrow(() -> new BadRequestException("Package not found!"));
  }

  @Transactional
  public Long createAnalysisPackage(AnalysisPackageDTO packageDTO) {
    AnalysisPackageEntity packageEntity = new AnalysisPackageEntity();
    toAnalysisPackageEntity(packageDTO, packageEntity);

    if (CollectionUtils.isNotEmpty(packageDTO.getAnalysisIds())) {
      List<AnalysisEntity> analyses =
          packageDTO.getAnalysisIds().stream()
              .map(id -> em.getReference(AnalysisEntity.class, id))
              .collect(Collectors.toList());
      packageEntity.setAnalyses(analyses);
    } else {
      packageEntity.setAnalyses(new ArrayList<>());
    }

    AnalysisPackageEntity saved = em.merge(packageEntity);

    return saved.getId();
  }

  @Transactional
  public Long updateAnalysisPackage(Long id, AnalysisPackageDTO analysisPackageDTO) {
    AnalysisPackageEntity packageEntity = em.find(AnalysisPackageEntity.class, id);
    if (packageEntity == null) {
      throw new BadRequestException("Package not found!");
    }

    toAnalysisPackageEntity(analysisPackageDTO, packageEntity);

    if (CollectionUtils.isNotEmpty(analysisPackageDTO.getAnalysisIds())) {
      List<AnalysisEntity> analyses =
          analysisPackageDTO.getAnalysisIds().stream()
              .map(dtoId -> em.getReference(AnalysisEntity.class, dtoId))
              .collect(Collectors.toList());
      packageEntity.setAnalyses(analyses);
    } else {
      packageEntity.setAnalyses(new ArrayList<>());
    }

    // Save the package entity
    AnalysisPackageEntity saved = em.merge(packageEntity);

    return saved.getId();
  }
}
