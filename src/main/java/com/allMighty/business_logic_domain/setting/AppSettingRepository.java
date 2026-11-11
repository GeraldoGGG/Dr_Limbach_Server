package com.allMighty.business_logic_domain.setting;

import static com.example.jooq.generated.Tables.APP_SETTING;

import com.allMighty.enumeration.SettingKey;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppSettingRepository {
  private final DSLContext dsl;

  public Optional<String> findValue(SettingKey key) {
    return dsl.select(APP_SETTING.SETTING_VALUE)
        .from(APP_SETTING)
        .where(APP_SETTING.SETTING_KEY.eq(key.name()))
        .fetchOptional(APP_SETTING.SETTING_VALUE);
  }

  public Optional<Long> findId(SettingKey key) {
    return dsl.select(APP_SETTING.ID)
        .from(APP_SETTING)
        .where(APP_SETTING.SETTING_KEY.eq(key.name()))
        .fetchOptional(APP_SETTING.ID);
  }
}
