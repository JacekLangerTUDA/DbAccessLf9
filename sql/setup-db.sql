-- create database if not yet exist
CREATE DATABASE IF NOT EXISTS a5;
CREATE DATABASE IF NOT EXISTS Versand;
CREATE DATABASE IF NOT EXISTS a34;
-- create user if not exist
CREATE USER IF NOT EXISTS 'lf9'@'%' IDENTIFIED BY 'lf9';
CREATE USER IF NOT EXISTS 'lf9'@'localhost' IDENTIFIED  BY 'lf9';
-- grant all privileges on database
GRANT ALL PRIVILEGES on a5.* TO 'lf9'@'localhost' ;
GRANT ALL PRIVILEGES on a5.* TO 'lf9'@'%' ;
GRANT ALL PRIVILEGES ON a34.* TO 'lf9'@'localhost';
GRANT ALL PRIVILEGES ON a34.* TO 'lf9'@'%';
GRANT ALL PRIVILEGES on Versand.* TO 'lf9'@'localhost' ;
GRANT ALL PRIVILEGES on Versand.* TO 'lf9'@'%';

-- flush privileges to update
FLUSH PRIVILEGES;