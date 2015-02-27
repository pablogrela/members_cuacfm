DROP TABLE Account;

CREATE TABLE Account(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    login VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(12) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);