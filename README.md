Создание базы данных и её визуализация

CREATE DATABASE `members`;
CREATE TABLE `projects`(
	projectId VARCHAR(6) PRIMARY KEY,
	projectName VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE `members` (
	memberId VARCHAR(6) PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	role VARCHAR(30) NOT NULL
);
CREATE TABLE `projects_members` (
	projectId VARCHAR(6) NOT NULL,
	memberId VARCHAR(6) NOT NULL,
    FOREIGN KEY (projectId) REFERENCES projects(projectId),
    FOREIGN KEY (memberId) REFERENCES members(memberId) 
);

![alt text](https://puu.sh/AjIkT/a50cd43381.png)
