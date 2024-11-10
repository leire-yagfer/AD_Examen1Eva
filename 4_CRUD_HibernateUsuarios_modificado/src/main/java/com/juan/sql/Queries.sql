DROP DATABASE IF EXISTS tutorial1;
CREATE DATABASE  tutorial1;
USE tutorial1;
CREATE TABLE  user (
  id integer NOT NULL AUTO_INCREMENT,
  firstname varchar(50) DEFAULT NULL,
  lastname varchar(100) DEFAULT NULL,
  dob   date DEFAULT NULL,  
  PRIMARY KEY (id)
);