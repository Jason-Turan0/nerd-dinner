DECLARE
  englishPk INT;
  spanishPk INT;
  usPk INT;
  gbPk INT;
  mxPk INT;
  esPk INT;

BEGIN
  INSERT INTO NERD_DINNER.LANGUAGES (LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('en', 'English');


  INSERT INTO NERD_DINNER.LANGUAGES (LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('es', 'Español');
  INSERT INTO NERD_DINNER.COUNTRIES (COUNTRY_CODE) VALUES ('US');
  INSERT INTO NERD_DINNER.COUNTRIES (COUNTRY_CODE) VALUES ('GB');
  INSERT INTO NERD_DINNER.COUNTRIES (COUNTRY_CODE) VALUES ('MX');
  INSERT INTO NERD_DINNER.COUNTRIES (COUNTRY_CODE) VALUES ('ES');



  SELECT LANGUAGE_PK INTO englishPk FROM NERD_DINNER.LANGUAGES WHERE LANGUAGE_CODE = 'en';
  SELECT LANGUAGE_PK INTO spanishPk FROM NERD_DINNER.LANGUAGES WHERE LANGUAGE_CODE = 'es';
  SELECT COUNTRY_PK INTO usPk FROM NERD_DINNER.COUNTRIES WHERE COUNTRY_CODE = 'US';
  SELECT COUNTRY_PK INTO gbPk FROM NERD_DINNER.COUNTRIES WHERE COUNTRY_CODE = 'GB';
  SELECT COUNTRY_PK INTO mxPk FROM NERD_DINNER.COUNTRIES WHERE COUNTRY_CODE = 'MX';
  SELECT COUNTRY_PK INTO esPk FROM NERD_DINNER.COUNTRIES WHERE COUNTRY_CODE = 'ES';


  INSERT INTO NERD_DINNER.LOCALES (LOCALE_ID, LANGUAGE_FK, COUNTRY_FK)
  VALUES ('en_US', englishPk, usPk);
  INSERT INTO NERD_DINNER.LOCALES (LOCALE_ID, LANGUAGE_FK, COUNTRY_FK)
  VALUES ('en_GB', englishPk, gbPk);
  INSERT INTO NERD_DINNER.LOCALES (LOCALE_ID, LANGUAGE_FK, COUNTRY_FK)
  VALUES ('es_ES', spanishPk, esPk);
  INSERT INTO NERD_DINNER.LOCALES (LOCALE_ID, LANGUAGE_FK, COUNTRY_FK)
  VALUES ('es_MX', spanishPk, mxPk);

  INSERT INTO NERD_DINNER.COUNTRY_DESCRIPTION (COUNTRY_FK, LANGUAGE_FK, COUNTRY_NAME)
  VALUES (usPk, englishPk, 'United States');
  INSERT INTO NERD_DINNER.COUNTRY_DESCRIPTION (COUNTRY_FK, LANGUAGE_FK, COUNTRY_NAME)
  VALUES (gbPk, englishPk, 'Great Britain');
  INSERT INTO NERD_DINNER.COUNTRY_DESCRIPTION (COUNTRY_FK, LANGUAGE_FK, COUNTRY_NAME)
  VALUES (esPk, spanishPk, 'España');
  INSERT INTO NERD_DINNER.COUNTRY_DESCRIPTION (COUNTRY_FK, LANGUAGE_FK, COUNTRY_NAME)
  VALUES (mxPk, spanishPk, 'Méjico');
END;