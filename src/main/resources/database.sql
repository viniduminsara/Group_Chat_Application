CREATE DATABASE IF NOT EXISTS chat_app;

USE chat_app;

CREATE TABLE user(
    employeeId VARCHAR(10) PRIMARY KEY,
    username VARCHAR(10) NOT NULL,
    image LONGBLOB
);