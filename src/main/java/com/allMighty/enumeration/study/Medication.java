package com.allMighty.enumeration.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Medication {
  HORMONAL_THERAPY_OR_CONTRACEPTIVES(
      "Terapi hormonale ose kontraceptivë hormonalë",
      "Hormonal therapy or hormonal contraceptives"),
  METFORMIN_BERBERINE_GLP1(
      "Metforminë, berberinë, agonistë GLP-1 ose preparate të ngjashme",
      "Metformin, berberine, GLP-1 agonists or similar preparations"),
  INOSITOL_OR_PCOS_SUPPLEMENTS(
      "Inositol apo preparate të tjerë suplemente për PCOS",
      "Inositol or other supplements for PCOS"),
  ANTIBIOTICS_LAST_3_MONTHS(
      "Përdorim i antibiotikëve gjatë 3 muajve të fundit",
      "Use of antibiotics in the last 3 months"),
  VITAMIN_D_LAST_3_MONTHS(
      "Përdorim i suplementeve të vitaminës D gjatë 3 muajve të fundit",
      "Use of vitamin D supplements in the last 3 months");

  private final String labelSq;
  private final String labelEn;
}
