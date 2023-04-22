-- create database if not yet exist
CREATE DATABASE IF NOT EXISTS a5;
CREATE DATABASE IF NOT EXISTS Versand;
CREATE DATABASE IF NOT EXISTS a34;
-- create user if not exist
CREATE USER IF NOT EXISTS 'jack'@'%' IDENTIFIED BY 'pass';
CREATE USER IF NOT EXISTS 'jack'@'localhost' IDENTIFIED  BY 'pass';
-- grant all privileges on database
GRANT ALL PRIVILEGES on a5.* TO 'jack'@'localhost' ;
GRANT ALL PRIVILEGES on a5.* TO 'jack'@'%' ;
GRANT ALL PRIVILEGES ON a34.* TO 'jack'@'localhost';
GRANT ALL PRIVILEGES ON a34.* TO 'jack'@'%';
GRANT ALL PRIVILEGES on Versand.* TO 'jack'@'localhost' ;
GRANT ALL PRIVILEGES on Versand.* TO 'jack'@'%';

-- flush privileges to update
FLUSH PRIVILEGES;