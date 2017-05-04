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
--     Seleccionar el fichero createTables-1.0.0.sql
--     Seleccionar el target schema members
--     Seleccionar la modalidad de carga Dump Structure, Dump Data o ambas.
--         Para members selecionar la modalidad Dump Structure and Data 
--         Para membersTest seleccionar la modalidad Dump Structure only
 
-- Terminal
-- 	Import MYSQL:
-- 		mysql -u root -p members < createTables-1.0.0.sql
 
--	Export MYSQL:
-- 		mysqldump -u root -p members > createTables-1.0.0.sql

 
use members;

DROP TABLE IF EXISTS Configuration;
DROP TABLE IF EXISTS DirectDebitPayPrograms;
DROP TABLE IF EXISTS DirectDebitPayMembers;
DROP TABLE IF EXISTS DirectDebit;
DROP TABLE IF EXISTS ReturnReason;
DROP TABLE IF EXISTS BankRemittance;
DROP TABLE IF EXISTS PayProgram;
DROP TABLE IF EXISTS FeeProgram;
DROP TABLE IF EXISTS UserPrograms;
DROP TABLE IF EXISTS PayMember;
DROP TABLE IF EXISTS FeeMember;
DROP TABLE IF EXISTS Inscription;
DROP TABLE IF EXISTS Training;
DROP TABLE IF EXISTS TrainingType;
DROP TABLE IF EXISTS Program;
DROP TABLE IF EXISTS ProgramType;
DROP TABLE IF EXISTS ProgramThematic;
DROP TABLE IF EXISTS ProgramCategory;
DROP TABLE IF EXISTS ProgramLanguage;
DROP TABLE IF EXISTS BankAccount;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS AccountType;
DROP TABLE IF EXISTS MethodPayment;


CREATE TABLE Configuration (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50),
    phone INT NOT NULL,
    feeMember DOUBLE(5,2) NOT NULL,
    feeProgram DOUBLE(5,2) NOT NULL,
    descriptionRule VARCHAR(500),
    CONSTRAINT ConfigurationId_PK PRIMARY KEY (id)
);


CREATE TABLE MethodPayment (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    directDebit BOOLEAN,
    description VARCHAR(100),
    CONSTRAINT MethodPaymentId_PK PRIMARY KEY (id),
    CONSTRAINT NameMethodPaymentUniqueKey UNIQUE (name)
);


CREATE TABLE AccountType (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    organization BOOLEAN,
    description VARCHAR(100),
    discount INT NOT NULL,
    CONSTRAINT AccountTypeId_PK PRIMARY KEY (id),
    CONSTRAINT NameAccountTypeUniqueKey UNIQUE (name)
);


CREATE TABLE Account(
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50),
    nickName VARCHAR(50),
    dni VARCHAR(10) NOT NULL,
    address VARCHAR(50) NOT NULL,
    cp VARCHAR(10),
    province VARCHAR(50),
    codeCountry VARCHAR(2),
    login VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(80) NOT NULL,
    phone VARCHAR(20),
    mobile VARCHAR(20) NOT NULL,
    methodPaymentId INT,
    accountTypeId INT,
    installments INT NOT NULL,
    student BOOLEAN,
    emitProgram BOOLEAN,
    dateBirth DATE NULL,
    active BOOLEAN,
    observations VARCHAR(500),
    personality VARCHAR(500),
    knowledge VARCHAR(500),   
    programName VARCHAR(50),
    role VARCHAR(20) NOT NULL,
    dateCreate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateDown TIMESTAMP NULL,
    token VARCHAR(500),
    -- bankAccountId INT,
    -- activeBankAccount INT,
    CONSTRAINT AccountId_PK PRIMARY KEY (id),
    CONSTRAINT AccountTypeId_FK FOREIGN KEY (accountTypeId) REFERENCES AccountType(id) ON DELETE Set NULL,
	CONSTRAINT MethodPaymentId_FK FOREIGN KEY (methodPaymentId) REFERENCES MethodPayment(id) ON DELETE Set NULL,
	-- CONSTRAINT BankAccount_FK FOREIGN KEY (bankAccountId) REFERENCES BankAccount(id),
	-- CONSTRAINT ActiveBankAccount_FK FOREIGN KEY (activeBankAccount) REFERENCES BankAccount(id),
	CONSTRAINT DniUniqueKey UNIQUE (dni),
    CONSTRAINT LoginUniqueKey UNIQUE (login),
    CONSTRAINT EmailUniqueKey UNIQUE (email)
);  
 
    
CREATE TABLE BankAccount(
    id INT NOT NULL auto_increment,
    accountId INT NOT NULL,
    bank VARCHAR(50) NOT NULL,
    bic VARCHAR(11),
    iban VARCHAR(34) NOT NULL,
    mandate VARCHAR(24) NOT NULL,
    dateMandate TIMESTAMP NULL,
    dateCreate TIMESTAMP NULL,
    active BOOLEAN,
    CONSTRAINT BankAccountId_PK PRIMARY KEY (id),
    CONSTRAINT BankAccount_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id)
); 


CREATE TABLE ProgramType (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT ProgramType_PK PRIMARY KEY (id),
    CONSTRAINT ProgramTypeUniqueKey UNIQUE (name)
);


CREATE TABLE ProgramThematic (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT ProgramThematic_PK PRIMARY KEY (id),
    CONSTRAINT ProgramThematicUniqueKey UNIQUE (name)
);


CREATE TABLE ProgramCategory (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT ProgramCategory_PK PRIMARY KEY (id),
    CONSTRAINT ProgramCategoryUniqueKey UNIQUE (name)
);


CREATE TABLE ProgramLanguage (
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT ProgramThematic_PK PRIMARY KEY (id),
    CONSTRAINT ProgramThematicUniqueKey UNIQUE (name)
);


CREATE TABLE Program(
    id INT NOT NULL auto_increment,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(5000),
    periodicity FLOAT NOT NULL,
    duration INT NOT NULL, 
    accountPayer INT,
    programType INT NOT NULL,
    programThematic INT NOT NULL,
    programCategory INT,
    programLanguage INT NOT NULL,   
    email VARCHAR(50),
    twitter VARCHAR(50),
    facebook VARCHAR(50),
    podcast VARCHAR(50),
    web VARCHAR(50),
    active BOOLEAN,
    dateCreate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateDown TIMESTAMP NULL,
    CONSTRAINT ProgramId_PK PRIMARY KEY (id),
    CONSTRAINT Program_AccountId_FK FOREIGN KEY (accountPayer) REFERENCES Account(id),
    CONSTRAINT Program_ProgramType_FK FOREIGN KEY (programType) REFERENCES ProgramType(id),
    CONSTRAINT Program_ProgramThematic_FK FOREIGN KEY (programThematic) REFERENCES ProgramThematic(id),
    CONSTRAINT Program_ProgramCategory_FK FOREIGN KEY (programThematic) REFERENCES ProgramThematic(id),
    CONSTRAINT Program_ProgramLanguage_FK FOREIGN KEY (programCategory) REFERENCES ProgramCategory(id),
    CONSTRAINT NameProgramUniqueKey UNIQUE (name)
); 


CREATE TABLE UserPrograms(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    programId INT NOT NULL,
    CONSTRAINT UserProgramsId_PK PRIMARY KEY (id),
    CONSTRAINT UserPrograms_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
    CONSTRAINT UserPrograms_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id),
    CONSTRAINT AccountProgramUniqueKey UNIQUE (accountId, programId)
);	



CREATE TABLE TrainingType(
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    required BOOLEAN,
    description VARCHAR(500),
    place VARCHAR(50),
    duration INT NOT NULL,
    hasTrainings BOOLEAN,
    CONSTRAINT TrainingId_PK PRIMARY KEY (id),
    CONSTRAINT NameTrainingTypeUniqueKey UNIQUE (name)
);
    

CREATE TABLE Training(	
    id INT NOT NULL auto_increment, 
    trainingTypeId INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    dateTraining TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dateLimit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(500),
    place VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    maxPlaces INT NOT NULL,
    countPlaces INT NOT NULL,
    close BOOLEAN,
    CONSTRAINT TrainingId_PK PRIMARY KEY (id),
    CONSTRAINT TrainingTypeId_FK FOREIGN KEY (trainingTypeId) REFERENCES TrainingType(id)
);


CREATE TABLE Inscription(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    trainingId INT NOT NULL,
    attend BOOLEAN,
    note VARCHAR(500),
    pass BOOLEAN,
    unsubscribe BOOLEAN,
    CONSTRAINT InscriptionId_PK PRIMARY KEY (id),
	CONSTRAINT AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT TrainingId_FK FOREIGN KEY (trainingId) REFERENCES Training(id) ON DELETE CASCADE
);
	
    
CREATE TABLE FeeProgram(
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
 	date DATE NOT NULL,
    price DOUBLE(5,2) NOT NULL ,
    dateLimit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(100),
    CONSTRAINT FeeProgram_PK PRIMARY KEY (id),
    CONSTRAINT DateUniqueKey UNIQUE (date)
);   


CREATE TABLE PayProgram(
    id INT NOT NULL auto_increment, 
    programId INT NOT NULL,
    feeProgramId INT NOT NULL,
    price DOUBLE(10,2) NOT NULL,
    accountPayer VARCHAR(50),
    state VARCHAR(20) NOT NULL,
    method VARCHAR(20),
   	idPayer VARCHAR(50),
   	idTxn VARCHAR(50),
   	emailPayer VARCHAR(50),
    datePay TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PayProgramId_PK PRIMARY KEY (id),
	CONSTRAINT PayProgram_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id),
	CONSTRAINT FeeProgramId_FK FOREIGN KEY (feeProgramId) REFERENCES FeeProgram(id)
	-- CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   
	

CREATE TABLE FeeMember(
    id INT NOT NULL auto_increment, 
    name VARCHAR(50) NOT NULL,
    year int NOT NULL,
    price DOUBLE(10,2) NOT NULL,
    dateLimit1 DATE NOT NULL,
    dateLimit2 DATE NOT NULL,
    description VARCHAR(100),
    CONSTRAINT FeeMemberId_PK PRIMARY KEY (id),
    CONSTRAINT YearUniqueKey UNIQUE (year)
);    


CREATE TABLE PayMember(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    feeMemberId INT NOT NULL,
    price DOUBLE(5,2) NOT NULL,
    installment INT,
    installments INT,
    state VARCHAR(20) NOT NULL,
    method VARCHAR(20),
   	idPayer VARCHAR(50),
   	idTxn VARCHAR(50),
   	emailPayer VARCHAR(50),
    datePay TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateCharge DATE,
    CONSTRAINT PayMemberId_PK PRIMARY KEY (id),
	CONSTRAINT PayMember_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT FeeMemberId_FK FOREIGN KEY (feeMemberId) REFERENCES FeeMember(id)
	-- CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   	


CREATE TABLE BankRemittance(
    id INT NOT NULL auto_increment, 
    dateDebit TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateCharge TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    monthCharge DATE,
    state VARCHAR(20) NOT NULL,
    CONSTRAINT BankRemittanceId_PK PRIMARY KEY (id)
);  


CREATE TABLE ReturnReason (
    id VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    CONSTRAINT ReturnReason_PK PRIMARY KEY (id)
);


CREATE TABLE DirectDebit(
   -- id referencia adeudo
    id VARCHAR(20) NOT NULL, 
    accountId INT NOT NULL,
    bankRemittanceId INT,
    dateCreate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateUpdate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    datePay TIMESTAMP NULL,
    state VARCHAR(20) NOT NULL,
    method VARCHAR(20),
    
    -- mandate VARCHAR(24) NULL,
    -- secuencia adeudose
    secuence VARCHAR(4) NULL,
    returnReasonId VARCHAR(50),
    
    -- paypal
    idPayer VARCHAR(50),
   	idTxn VARCHAR(50),
   	emailPayer VARCHAR(50),
   	
    CONSTRAINT DirectDebitId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebit_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
    CONSTRAINT DirectDebit_BankRemittance_FK FOREIGN KEY (bankRemittanceId) REFERENCES BankRemittance(id),
    CONSTRAINT DirectDebit_ReturnReason_FK FOREIGN KEY (returnReasonId) REFERENCES ReturnReason(id),
    CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);  


CREATE TABLE DirectDebitPayPrograms(
    id INT NOT NULL auto_increment, 
    directDebitId VARCHAR(20) NOT NULL,
    payProgramId INT NOT NULL,
    CONSTRAINT DirectDebitPayProgramsId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebitPayPrograms_DirectDebitId_FK FOREIGN KEY (directDebitId) REFERENCES DirectDebit(id),
    CONSTRAINT DirectDebitPayPrograms_PayProgramId_FK FOREIGN KEY (payProgramId) REFERENCES PayProgram(id) ON DELETE CASCADE
);		


CREATE TABLE DirectDebitPayMembers(
    id INT NOT NULL auto_increment,
    directDebitId VARCHAR(20) NOT NULL,
    payMemberId INT NOT NULL,
    CONSTRAINT DirectDebitPayMembersId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebitPayMembers_DirectDebitId_FK FOREIGN KEY (directDebitId) REFERENCES DirectDebit(id),
    CONSTRAINT DirectDebitPayMembers_PayMemberId_FK FOREIGN KEY (payMemberId) REFERENCES PayMember(id) ON DELETE CASCADE
);		


CREATE TABLE Event(
    id INT NOT NULL auto_increment,
    accountId INT NOT NULL,
    dateEvent TIMESTAMP NOT NULL,
    priority INT NOT NULL,
    description VARCHAR(500) NOT NULL,
    CONSTRAINT EventId_PK PRIMARY KEY (id),
    CONSTRAINT Event_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id)
); 


-- Solo para la base de datos principal, para el test no se debe cargar
-- Insert Configuration:
insert into Configuration values (1, 'CuacFM', 'cuacfm@hotmail.com', 981666666, 24, 25, 'Comprométome a coñecer e cumprir a normativa interna da asociación, así coma a asumir a responsabilidade das informacións e opinións que difunda en antena e a facer un bo uso das instalacións e material da asociación.

Se marquei na categoría "soci@", estou a solicitar formalmente o ingreso na asociación cultural  Colectivo de Universitarios ACtivos, cousa que NON ACONTECE, se marquei as opcións "simpatizante" ou "patrocinador web"            ');


insert into Account values 
(1, 'admin', '', null, 'C04496998', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, null, null, 1, false, false, null, true, '', '', '', '', 'ROLE_ADMIN', 'ROLE_REPORT, ROLE_TRAINER', null, null, null);


-- Insert Method Payment:
insert into MethodPayment values (1, 'Exento', false, 'No tiene que pagar');
insert into MethodPayment values (2, 'Efectivo', false, 'Pago en efectivo');
insert into MethodPayment values (3, 'Domiciliado', true, 'Domiciliado');
insert into MethodPayment values (4, 'Paypal', false, 'Paypal');	
insert into MethodPayment values (5, 'Ingreso', false, 'Pago mediante un ingreso bancario');	


-- Insert Account Types:
insert into AccountType values (1, 'Exento', false, 'No tiene que pagar', 100);
insert into AccountType values (2, 'Adulto', false, 'Tarifa adulta', 0);
insert into AccountType values (3, 'Juvenil', false, 'Tarifa juvenil', 50);
insert into AccountType values (4, 'Jubilado', false, 'Tarifa adulta', 50);
insert into AccountType values (5, 'Persona Juridica', true, 'Tarifa adulta', 0);
	

-- Insert Program Type:
insert into ProgramType values (1, 'Outro', 'Outro');	
insert into ProgramType values (2, 'Musical', 'Musical');
insert into ProgramType values (3, 'Informativo', 'Informativo');
insert into ProgramType values (4, 'Magazine', 'Magazine');
insert into ProgramType values (5, 'Debate', 'Debate');	
insert into ProgramType values (6, 'Educativo', 'Educativo');	
insert into ProgramType values (7, 'Entrevistas', 'Entrevistas');	


-- Insert Program Thematic:
insert into ProgramThematic values (1, 'Otros temas', 'Otros temas');	
insert into ProgramThematic values (2, 'Sociedade', ' Sociedade');
insert into ProgramThematic values (3, 'Política', 'Política');
insert into ProgramThematic values (4, 'Cultura', ' Cultura');
insert into ProgramThematic values (5, 'Deportes', 'Deportes');	
insert into ProgramThematic values (6, 'Humor', 'Humor');	
insert into ProgramThematic values (7, 'Actualidade', 'Actualidade');	
insert into ProgramThematic values (8, 'Ciencia', 'Ciencia');	


-- Insert ProgramCategory:
-- Examples, use category podcast, search in web
insert into ProgramCategory values (1, 'Otros', ' Otros');
insert into ProgramCategory values (2, 'Sociedade', ' Sociedade');
insert into ProgramCategory values (3, 'Política', 'Política');
insert into ProgramCategory values (4, 'Cultura', ' Cultura');
insert into ProgramCategory values (5, 'Deportes', 'Deportes');	
insert into ProgramCategory values (6, 'Humor', 'Humor');	
insert into ProgramCategory values (7, 'Actualidade', 'Actualidade');	
insert into ProgramCategory values (8, 'Ciencia', 'Ciencia');	


-- Insert Program Thematic:
insert into ProgramLanguage values (1, 'Outro', ' Outro');
insert into ProgramLanguage values (2, 'Castelán', ' Castelán');
insert into ProgramLanguage values (3, 'Galego', 'Galego');
insert into ProgramLanguage values (4, 'Inglés', ' Inglés');


