CREATE TABLE app_setting (
                             id BIGSERIAL PRIMARY KEY,
                             version BIGINT,
                             setting_key VARCHAR(255) NOT NULL UNIQUE,
                             setting_value VARCHAR(255) NOT NULL
);

INSERT INTO app_setting (version, setting_key, setting_value)
VALUES (0, 'STUDY_APPLICATION_ENABLED', 'false');
