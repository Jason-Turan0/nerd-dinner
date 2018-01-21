CREATE TABLE LOCALES (
  LOCALE_PK   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  LOCALE_ID   NVARCHAR(10)       NOT NULL,
  LANGUAGE_FK INT                NOT NULL,
  COUNTRY_FK  INT                NOT NULL,

  FOREIGN KEY (LANGUAGE_FK) REFERENCES LANGUAGES (LANGUAGE_PK),
  FOREIGN KEY (COUNTRY_FK) REFERENCES COUNTRIES (COUNTRY_PK)
)