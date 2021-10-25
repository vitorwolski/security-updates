CREATE TABLE MS_SEC_UPD
(
   ID VARCHAR(30) PRIMARY KEY,
   ALIAS VARCHAR(30) NULL,
   DOCUMENT_TITLE VARCHAR(255) NULL,
   SEVERITY VARCHAR(255) NULL,
   INI_RLS_DATE TIMESTAMP NULL,
   CUR_RLS_DATE TIMESTAMP NULL,
   CVRF_URL VARCHAR(255)
)