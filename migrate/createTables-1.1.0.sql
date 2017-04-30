--
-- Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

 
-- INFO English
-- 	Creating the tables to use the application
-- 	To import the tables to the database, it must exist
-- 	Can be done by command line or with the workbench
 

-- INFO Español
-- 	Creacion de las tablas para usar la aplicación 
-- 	Para importar la tablas a la base de datos, esta debe existir
-- 	Se puede hacer por linea de comandos o con el workbench
 

-- Workbench 
-- 	Se accede a Data Import / Import From Disk / Import from Sel-container File
--     Seleccionar el fichero createTables-1.1.0.sql
--     Seleccionar el target schema members
--     Seleccionar la modalidad de carga Dump Structure, Dump Data o ambas.
--         Para members selecionar la modalidad Dump Structure and Data 
--         Para membersTest seleccionar la modalidad Dump Structure only
 
-- Terminal
-- 	Import MYSQL:
-- 		mysql -u root -p members < createTables-1.1.0.sql
 
--	Export MYSQL:
-- 		mysqldump -u root -p members > createTables-1.1.0.sql


use members;

DROP TABLE IF EXISTS Incidence;
DROP TABLE IF EXISTS Report;


CREATE TABLE Report(
    id BIGINT NOT NULL auto_increment,
    accountId INT NOT NULL,
    programId INT NOT NULL,
    dirt TINYINT NOT NULL, 
    tidy TINYINT NOT NULL, 
    configuration TINYINT NOT NULL, 
    openDoor BOOLEAN NOT NULL, 
    viewMembers BOOLEAN NOT NULL, 
    location VARCHAR(50),
    description VARCHAR(500),
    file VARCHAR(100),
    files VARCHAR(500),
    answer VARCHAR(5000),	
    dateCreate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateRevision TIMESTAMP NULL,
    active BOOLEAN NOT NULL, 
    CONSTRAINT Report_PK PRIMARY KEY (id),
    CONSTRAINT Report_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
    CONSTRAINT Report_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id)
); 