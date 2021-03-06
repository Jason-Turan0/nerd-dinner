CREATE TABLE NERD_DINNERS(
  NERD_DINNER_PK     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
  DINNER_DATE        TIMESTAMP,
  ADDRESS_FK         INT           NOT NULL,
  MAXIMUM_ATTENDANCE INT           NOT NULL,
  TYPE               NVARCHAR2(255) NOT NULL,
  CREATOR_NERD_FK    INT           NOT NULL,
  FOREIGN KEY (ADDRESS_FK) REFERENCES ADDRESSES(ADDRESS_PK),
  FOREIGN KEY (CREATOR_NERD_FK) REFERENCES NERDS(NERD_PK)
)