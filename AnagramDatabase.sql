-- create a user and grant privileges to that user
GRANT ALL PRIVILEGES ON anagram.* TO Anagram@localhost IDENTIFIED BY 'Anagram2019' WITH GRANT OPTION;

-- create and select the database
DROP DATABASE IF EXISTS anagram;
CREATE DATABASE anagram;
USE anagram;

-- create the table
DROP TABLE IF EXISTS Anagram;

CREATE TABLE Anagram
(
    AnagramID   INT          PRIMARY KEY  AUTO_INCREMENT,
    FirstAnagram    VARCHAR(50), 
    SecondAnagram   VARCHAR(50)
);

-- insert some rows the table
INSERT INTO Anagram VALUES 
(3, 'ear', 'are'),
(4, 'acre', 'race'),
(5, 'asleep', 'elapse'),
(6, 'read', 'dare');
