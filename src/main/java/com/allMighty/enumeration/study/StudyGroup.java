package com.allMighty.enumeration.study;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyGroup {
  CONTROL(
      "Grupi i kontrollit – Gra pa diagnozë të PMOS(PCOS)",
      "Control group – Women without a PCOS diagnosis"),
  PCOS(
      "Grupi PCOS – Gra me diagnozë të PMOS(PCOS)",
      "PCOS group – Women diagnosed with PCOS"),
  NOT_SURE("Nuk jam e sigurt", "I am not sure");

  private final String labelSq;
  private final String labelEn;
}
