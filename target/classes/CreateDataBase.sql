DROP TABLE Configuration;
DROP TABLE DirectDebitPayPrograms;
DROP TABLE DirectDebitPayMembers;
DROP TABLE DirectDebit;
DROP TABLE BankRemittance;
DROP TABLE PayProgram;
DROP TABLE FeeProgram;
DROP TABLE UserPrograms;
DROP TABLE PayMember;
DROP TABLE FeeMember;
DROP TABLE Inscription;
DROP TABLE Training;
DROP TABLE TrainingType;
DROP TABLE Program;
DROP TABLE BankAccount;
DROP TABLE Account;
DROP TABLE AccountType;
DROP TABLE MethodPayment;


CREATE TABLE Configuration (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30),
    phone INT NOT NULL,
    feeMember DOUBLE(5,2) NOT NULL,
    feeProgram DOUBLE(5,2) NOT NULL,
    descriptionRul VARCHAR(500),
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
    dateBirth TIMESTAMP NULL,
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
    description VARCHAR(100),
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
    dateTraining TIMESTAMP NOT NULL,
    dateLimit TIMESTAMP NOT NULL,
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
    name VARCHAR(30) NOT NULL,
 	date DATE NOT NULL,
    price DOUBLE(5,2) NOT NULL,
    dateLimit TIMESTAMP NOT NULL,
    description VARCHAR(100),
    CONSTRAINT FeeProgram_PK PRIMARY KEY (id),
    CONSTRAINT DateUniqueKey UNIQUE (date)
);   


CREATE TABLE PayProgram(
    id INT NOT NULL auto_increment, 
    programId INT NOT NULL,
    feeProgramId INT NOT NULL,
    directDebitId INT,
    price DOUBLE(5,2) NOT NULL,
    accountPayer VARCHAR(30),
    state VARCHAR(20) NOT NULL,
    method VARCHAR(20) NOT NULL,
   	idPayer VARCHAR(30),
   	idTxn VARCHAR(30),
   	emailPayer VARCHAR(30),
    datePay TIMESTAMP NULL,
    CONSTRAINT PayProgramId_PK PRIMARY KEY (id),
	CONSTRAINT PayProgram_ProgramId_FK FOREIGN KEY (programId) REFERENCES Program(id),
	CONSTRAINT FeeProgramId_FK FOREIGN KEY (feeProgramId) REFERENCES FeeProgram(id),
	CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   
	

CREATE TABLE FeeMember(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
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
    datePay TIMESTAMP NULL,
    dateCharge DATE,
    CONSTRAINT PayMemberId_PK PRIMARY KEY (id),
	CONSTRAINT PayMember_AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT FeeMemberId_FK FOREIGN KEY (feeMemberId) REFERENCES FeeMember(id),
	CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
);   	


CREATE TABLE BankRemittance(
    id INT NOT NULL auto_increment, 
    dateDebit TIMESTAMP,
    dateCharge TIMESTAMP,
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

	
-- Insert Account Types:
insert into AccountType values (1, 'No', false, 'No tiene que pagar', 100);
insert into AccountType values (2, 'Adulto', false, 'Tarifa adulta', 0);
insert into AccountType values (3, 'Juvenil', false, 'Tarifa juvenil', 50);
insert into AccountType values (4, 'Jubilado', false, 'Tarifa adulta', 50);
insert into AccountType values (5, 'Persona Juridica', true, 'Tarifa adulta', 0);


-- Insert Method Payment:
insert into MethodPayment values (1, 'No', false, 'No tiene que pagar');
insert into MethodPayment values (2, 'Efectivo', false, 'Pago en efectivo');
insert into MethodPayment values (3, 'Domiciliado', true, 'Domiciliado');
insert into MethodPayment values (4, 'Paypal', false, 'Paypal');	
	

-- Insert Account:
insert into Account values 
(1, 'user', null, '12345678A', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'user', 'user@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(2, 'admin', null, '12345678B', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 1, 1, 1, false, null, true, '', '', 'ROLE_ADMIN');

insert into Account values 
(3, 'trainer', null, '12345678C', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'trainer', 'trainer@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 1, 1, 1, false, null, true, '', '', 'ROLE_TRAINER');

insert into Account values 
(4, 'Pablo Grela', null, '12345678D', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.grela', 'pablo@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 3, 3, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(5, 'Manuel Fernandez', null, '12345678E', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manuel.fernandez', 'manu@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(6, 'Lorena Borrazás', null, '12345678F', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.borrazas', 'lore@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(7, 'Lorena Fernandez', null, '12345678Z', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.fernandez', 'loref@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');
	
insert into Account values 
(8, 'Manuel Borrazás', null, '12345678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manu.borrazas', 'manuf@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 3, 1 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(9, 'Pablo Martínez Pérez', null, '12347678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.martinez.perez', 'pmp@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 3, 2 , 1, false, null, true, null, '', 'ROLE_USER');


	
insert into BankAccount values 	
(1, 4, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '4_12345678D_1', "2015-04-10 11:00", true);		
insert into BankAccount values 	
(2, 8, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '8_12345678P_1', "2015-04-10 11:00", true);	
insert into BankAccount values 	
(3, 9, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '9_12347678D_1', "2015-04-10 11:00", false);
insert into BankAccount values 	
(4, 9, 'A Banca', 'BancaMMXXX', 'ES7620770024003102575766', '9_12347678D_2', "2015-04-10 11:00", true);	
	

-- Insert Program:
insert into Program values 
(1, 'Program 1', 1, 1, 'Description of Program 1', true, 4);
insert into Program values 
(2, 'Program 2', 1, 2, 'Description of Program 2', true, 4);
insert into Program values 
(3, 'Program 3', 1, 2, 'Description of Program 3', true, null);
insert into Program values 
(4, 'Program 4', 1, 2, 'Description of Program 4', true, null);
insert into Program values 
(5, 'Program 5', 1, 2, 'Description of Program 5', true, 8);


-- Relacionated Program with User:
insert into UserPrograms values (1, 4, 1);
insert into UserPrograms values (2, 5, 1);
insert into UserPrograms values (3, 4, 2);
insert into UserPrograms values (4, 9, 3);
insert into UserPrograms values (5, 8, 3);
insert into UserPrograms values (6, 9, 4);
insert into UserPrograms values (7, 7, 4);
insert into UserPrograms values (8, 8, 5);

-- Insert Trainings Type:
insert into TrainingType values 
(1, 'Camara', true, 'Se enseñara a grabar', 'Estudio Cuac', 1.30, true);
insert into TrainingType values 
(2, 'Producción', true, 'Se enseñara producir un programa', 'Estudio Cuac', 2.00, true);
insert into TrainingType values 
(3, 'Técnica e Locución', false, 'Se enseñara a hablar', 'Estudio Cuac', 1, true);
insert into TrainingType values 
(4, 'Practica', false, 'Se enseñara a posicionarse en el estudio', 'Estudio Cuac', 2.10, true);



-- Insert Trainings:
insert into Training values 
(1, 1, 'Camara', "2015-06-10 11:00", null, 'Se enseñara a grabar', 'Estudio Cuac', 1.30, 10, 0, true);
insert into Training values 
(2, 2, 'Locución', "2015-06-17 11:00", "2015-06-15 11:00", 'Se enseñara a hablar', 'Estudio Cuac', 2.00, 10, 0, 1, true);
insert into Training values 
(3, 3, 'Redacción', "2015-07-24 11:00",  "2015-07-22 11:00", 'Se enseñara a redactar el programa', 'Estudio Cuac', 1, 10, 0, false);
insert into Training values 
(4, 4, 'Audacity', "2015-07-31 11:00", "2015-07-29 11:00", 'Se enseñara a posicionarse en el estudio', 'Estudio Cuac', 2.10, 10, 0, false);
insert into Training values 
(5, 1, 'Camara', "2015-07-10 11:00", "2015-07-08 11:00", 'Se enseñara a grabar', 'Estudio Cuac', 1.30, 10, 0, false);
insert into Training values 
(6, 2, 'Locución', "2015-06-17 11:00", "2015-06-15 11:00", 'Se enseñara a hablar', 'Estudio Cuac', 2.00, 10, 0, false);



-- Insert Inscriptions:
insert into Inscription values 
(1, 4, 1, true, " ", true, false);
insert into Inscription values 
(2, 4, 2, true, "no atendio", false, false);
insert into Inscription values 
(3, 4, 3, false, " ", false, false);
insert into Inscription values 
(4, 4, 4, false, " ", false, false);

insert into Inscription values 
(5, 5, 1, true, "atendio mucho", true, false);
insert into Inscription values 
(6, 5, 2, false, "no vino", false, false);
insert into Inscription values 
(7, 5, 3, false, " ", false, false);
insert into Inscription values 
(8, 5, 4, false, " ", false, false);