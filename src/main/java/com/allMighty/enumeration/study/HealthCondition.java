package com.allMighty.enumeration.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HealthCondition {
  PREGNANCY("Shtatzëni", "Pregnancy"),
  ARTERIAL_HYPERTENSION("Hipertension arterial", "Arterial hypertension"),
  ACTIVE_SMOKING("Duhanpirje aktive", "Active smoking"),
  THYROID_DYSFUNCTION("Disfunksion i gjëndrës tiroide", "Thyroid dysfunction"),
  CUSHING_SYNDROME("Sindromë Cushing", "Cushing syndrome"),
  ADRENAL_DISORDER("Çrregullime të gjëndrave adrenale", "Adrenal gland disorders"),
  HYPERPROLACTINEMIA("Hiperprolaktinemi", "Hyperprolactinemia"),
  GASTROINTESTINAL_DISEASE("Sëmundje gastrointestinale", "Gastrointestinal disease"),
  DIABETES_MELLITUS("Diabet mellitus", "Diabetes mellitus");

  private final String labelSq;
  private final String labelEn;
}
