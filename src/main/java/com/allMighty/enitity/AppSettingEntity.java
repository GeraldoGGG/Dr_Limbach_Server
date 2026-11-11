package com.allMighty.enitity;

import com.allMighty.enitity.abstractEntity.AbstractEntity;
import com.allMighty.enumeration.SettingKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_setting")
public class AppSettingEntity extends AbstractEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "setting_key", nullable = false, unique = true)
  private SettingKey settingKey;

  @Column(name = "setting_value", nullable = false)
  private String settingValue;
}
