drop table if exists VALIDATION_CONNPOOL;
CREATE TABLE VALIDATION_CONNPOOL (
	i_id int NOT NULL PRIMARY KEY
)
ENGINE=MyISAM;
INSERT INTO VALIDATION_CONNPOOL (i_id) VALUES (1);