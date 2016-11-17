DROP TABLE IF EXISTS Configuration;
DROP TABLE IF EXISTS DirectDebitPayPrograms;
DROP TABLE IF EXISTS DirectDebitPayMembers;
DROP TABLE IF EXISTS DirectDebit;
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
DROP TABLE IF EXISTS BankAccount;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS AccountType;
DROP TABLE IF EXISTS MethodPayment;


CREATE TABLE Configuration (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30),
    phone INT NOT NULL,
    feeMember DOUBLE(5,2) NOT NULL,
    feeProgram DOUBLE(5,2) NOT NULL,
    descriptionRule VARCHAR(500),
    CONSTRAINT ConfigurationId_PK PRIMARY KEY (id)
);


CREATE TABLE MethodPayment (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    directDebit BOOLEAN,
    description VARCHAR(100),
    CONSTRAINT MethodPaymentId_PK PRIMARY KEY (id),
    CONSTRAINT NameMethodPaymentUniqueKey UNIQUE (name)
);


CREATE TABLE AccountType (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    organization BOOLEAN,
    description VARCHAR(100),
    discount INT NOT NULL,
    CONSTRAINT AccountTypeId_PK PRIMARY KEY (id),
    CONSTRAINT NameAccountTypeUniqueKey UNIQUE (name)
);


CREATE TABLE Account(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    nickName VARCHAR(30),
    dni VARCHAR(30) NOT NULL,
    address VARCHAR(30) NOT NULL,
    cp VARCHAR(30),
    province VARCHAR(30),
    codeCountry VARCHAR(2),
    login VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(80) NOT NULL,
    phone VARCHAR(20),
    mobile VARCHAR(20) NOT NULL,
    methodPaymentId INT,
    accountTypeId INT,
    installments INT NOT NULL,
    student BOOLEAN,
    dateBirth DATE NULL,
    active BOOLEAN,
    observations VARCHAR(500),
    programName VARCHAR(30),
    role VARCHAR(20) NOT NULL,
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
    bank VARCHAR(30) NOT NULL,
    bic VARCHAR(11) NOT NULL,
    iban VARCHAR(34) NOT NULL,
    mandate VARCHAR(24) NOT NULL,
    dateCreated TIMESTAMP NULL,
    active BOOLEAN,
    CONSTRAINT BankAccountId_PK PRIMARY KEY (id),
    CONSTRAINT BankAccount_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id)
); 


CREATE TABLE Program(
    id INT NOT NULL auto_increment,
    name VARCHAR(30) NOT NULL,
    periodicity FLOAT NOT NULL,
    duration INT NOT NULL,
    description VARCHAR(500),
    active BOOLEAN,
    accountPayer INT,
    CONSTRAINT ProgramId_PK PRIMARY KEY (id),
    CONSTRAINT Program_AccountId_FK FOREIGN KEY (accountPayer) REFERENCES Account(id),
    CONSTRAINT NameProgramUniqueKey UNIQUE (name)
); 


CREATE TABLE UserPrograms(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    programId INT NOT NULL,
    CONSTRAINT UserProgramsId_PK PRIMARY KEY (id),
    CONSTRAINT UserPrograms_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
    CONSTRAINT UserPrograms_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id),
    CONSTRAINT AccountProgramUniqueKey UNIQUE (accountId,programId)
);	


CREATE TABLE TrainingType(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    required BOOLEAN,
    description VARCHAR(500),
    place VARCHAR(30),
    duration INT NOT NULL,
    hasTrainings BOOLEAN,
    CONSTRAINT TrainingId_PK PRIMARY KEY (id),
    CONSTRAINT NameTrainingTypeUniqueKey UNIQUE (name)
);
    

CREATE TABLE Training(	
    id INT NOT NULL auto_increment, 
    trainingTypeId INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    dateTraining TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dateLimit TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(500),
    place VARCHAR(30) NOT NULL,
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
	CONSTRAINT TrainingId_FK FOREIGN KEY (trainingId) REFERENCES Training(id)
);
	
    
CREATE TABLE FeeProgram(
    id INT NOT NULL auto_increment, 
    name VARCHAR(40) NOT NULL,
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
    price DOUBLE(5,2) NOT NULL,
    accountPayer VARCHAR(30),
    state VARCHAR(20) NOT NULL,
    method VARCHAR(20) NOT NULL,
   	idPayer VARCHAR(30),
   	idTxn VARCHAR(30),
   	emailPayer VARCHAR(30),
    datePay TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PayProgramId_PK PRIMARY KEY (id),
	CONSTRAINT PayProgram_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id),
	CONSTRAINT FeeProgramId_FK FOREIGN KEY (feeProgramId) REFERENCES FeeProgram(id),
	CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   
	

CREATE TABLE FeeMember(
    id INT NOT NULL auto_increment, 
    name VARCHAR(40) NOT NULL,
    year int NOT NULL,
    price DOUBLE(5,2) NOT NULL,
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
    method VARCHAR(20) NOT NULL,
   	idPayer VARCHAR(30),
   	idTxn VARCHAR(30),
   	emailPayer VARCHAR(30),
    datePay TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateCharge DATE,
    CONSTRAINT PayMemberId_PK PRIMARY KEY (id),
	CONSTRAINT PayMember_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT FeeMemberId_FK FOREIGN KEY (feeMemberId) REFERENCES FeeMember(id),
	CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   	


CREATE TABLE BankRemittance(
    id INT NOT NULL auto_increment, 
    dateDebit TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    dateCharge TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    monthCharge DATE,
    state VARCHAR(20) NOT NULL,
    CONSTRAINT BankRemittanceId_PK PRIMARY KEY (id)
);  


CREATE TABLE DirectDebit(
   -- id referencia adeudo
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    bankRemittanceId INT NOT NULL,
    concept VARCHAR(140) NOT NULL,
    price DOUBLE(5,2) NOT NULL,
    mandate VARCHAR(24) NOT NULL,
    -- secuencia adeudose
    secuence VARCHAR(4) NOT NULL,
    state VARCHAR(20) NOT NULL,
    CONSTRAINT DirectDebitId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebit_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
    CONSTRAINT DirectDebit_BankRemittance_FK FOREIGN KEY (bankRemittanceId) REFERENCES BankRemittance(id)
);  


CREATE TABLE DirectDebitPayPrograms(
    id INT NOT NULL auto_increment, 
    directDebitId INT NOT NULL,
    payProgramId INT NOT NULL,
    CONSTRAINT DirectDebitPayProgramsId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebitPayPrograms_DirectDebitId_FK FOREIGN KEY (directDebitId) REFERENCES DirectDebit(id),
    CONSTRAINT DirectDebitPayPrograms_PayProgramId_FK FOREIGN KEY (payProgramId) REFERENCES PayProgram(id)
);		


CREATE TABLE DirectDebitPayMembers(
    id INT NOT NULL auto_increment,
    directDebitId INT NOT NULL,
    payMemberId INT NOT NULL,
    CONSTRAINT DirectDebitPayMembersId_PK PRIMARY KEY (id),
    CONSTRAINT DirectDebitPayMembers_DirectDebitId_FK FOREIGN KEY (directDebitId) REFERENCES DirectDebit(id),
    CONSTRAINT DirectDebitPayMembers_PayMemberId_FK FOREIGN KEY (payMemberId) REFERENCES PayMember(id)
);		


-- Insert Configuration:
insert into Configuration values (1, 'CuacFM', 'cuacfm@hotmail.com', 981666666, 24, 25, 'Comprométome a coñecer e cumprir a normativa interna da asociación, así coma a asumir a responsabilidade das informacións e opinións que difunda en antena e a facer un bo uso das instalacións e material da asociación.

Se marquei na categoría "soci@", estou a solicitar formalmente o ingreso na asociación cultural  Colectivo de Universitarios ACtivos, cousa que NON ACONTECE, se marquei as opcións "simpatizante" ou "patrocinador web"            ');


insert into Account values 
(1, 'admin', null, '12345678B', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, null, null, 1, false, null, true, '', '', 'ROLE_ADMIN');



	
