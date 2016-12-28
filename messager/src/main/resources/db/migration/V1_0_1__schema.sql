 create table ENTRIES ( ID INTEGER PRIMARY KEY,LINK VARCHAR(200), NAME TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci, OWNER VARCHAR(50), DOWNLOAD_STATUS TINYINT(1), DOWNLOAD_STARTED TIMESTAMP, DOWNLOAD_COMPLETED TIMESTAMP, PATH TEXT CHARACTER SET utf8 COLLATE utf8_unicode_ci);
 ALTER TABLE ENTRIES MODIFY COLUMN ID INTEGER AUTO_INCREMENT;
 ALTER TABLE ENTRIES ADD CONSTRAINT constraint1 UNIQUE(LINK);