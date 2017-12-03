CREATE TABLE NERD_EMAILS(
  NERD_EMAIL_PK INT AUTO_INCREMENT PRIMARY KEY ,
  NERD_FK INT NOT NULL,
  EMAIL NVARCHAR(500),
  NERD_CONTACT_TYPE_FK INT NOT NULL,
  LAST_UPDATE_DATE TIMESTAMP NOT NULL,
  EXPIRED_DATE TIMESTAMP NULL,
  FOREIGN KEY (NERD_FK) REFERENCES NERDS(NERD_PK),
  FOREIGN KEY (NERD_CONTACT_TYPE_FK) REFERENCES NERD_CONTACT_TYPE(NERD_CONTACT_TYPE_PK)
)