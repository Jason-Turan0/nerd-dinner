CREATE TABLE LANGUAGES (
  LANGUAGE_PK INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
  LANGUAGE_CODE NVARCHAR2(10)       NOT NULL,
  LANGUAGE_NAME NVARCHAR2(255)      NOT NULL
)