CREATE TABLE NERD_PHONES(
  NERD_PHONE_PK INT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
  NERD_FK INT NOT NULL,
  AREA_CODE NVARCHAR(3) NOT NULL,
  CENTRAL_OFFICE_PREFIX NVARCHAR(3) NOT NULL,
  LINE_NUMBER NVARCHAR(4) NOT NULL,
  LAST_UPDATE_DATE TIMESTAMP NOT NULL,
  EXPIRED_DATE TIMESTAMP NULL,
  NERD_CONTACT_TYPE_FK INT NOT NULL,
  FOREIGN KEY (NERD_FK) REFERENCES NERDS(NERD_PK),
  FOREIGN KEY (NERD_CONTACT_TYPE_FK) REFERENCES NERD_CONTACT_TYPE(NERD_CONTACT_TYPE_PK)
)