DROP TABLE UserPayInscription;
DROP TABLE PayInscription;
DROP TABLE Inscription;
DROP TABLE Training;
DROP TABLE TrainingType;
DROP TABLE Account;
DROP TABLE AccountType;
DROP TABLE MethodPayment;


CREATE TABLE MethodPayment (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT MethodPaymentId_PK PRIMARY KEY (id),
    CONSTRAINT NameMethodPaymentUniqueKey UNIQUE (name)
);


CREATE TABLE AccountType (
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    description VARCHAR(100),
    discount INT NOT NULL,
    CONSTRAINT AccountTypeId_PK PRIMARY KEY (id),
    CONSTRAINT NameAccountTypeUniqueKey UNIQUE (name)
);


CREATE TABLE Account(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    dni VARCHAR(30) NOT NULL,
    address VARCHAR(30) NOT NULL,
    login VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(80) NOT NULL,
    phone INT,
    mobile INT NOT NULL,
    methodPaymentId INT,
    accountTypeId INT,   
    installments INT NOT NULL,
    student BOOLEAN,
    dateBirth TIMESTAMP NULL,
    active BOOLEAN,
    observations VARCHAR(500),
    programName VARCHAR(30),
    role VARCHAR(12) NOT NULL,
    CONSTRAINT AccountId_PK PRIMARY KEY (id),
    CONSTRAINT AccountTypeId_FK FOREIGN KEY (accountTypeId) REFERENCES AccountType(id) ON DELETE Set NULL,
	CONSTRAINT MethodPaymentId_FK FOREIGN KEY (methodPaymentId) REFERENCES MethodPayment(id) ON DELETE Set NULL,
	CONSTRAINT DniUniqueKey UNIQUE (dni),
    CONSTRAINT LoginUniqueKey UNIQUE (login),
    CONSTRAINT EmailUniqueKey UNIQUE (email)
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

    
CREATE TABLE Service(
    id INT NOT NULL auto_increment, 
    typeFeed VARCHAR(30) NOT NULL,
    periodicity int NOT NULL,
    price DECIMAL(4,2) NOT NULL,
    description VARCHAR(100),
    CONSTRAINT ServiceId_PK PRIMARY KEY (id)
	);  
	

CREATE TABLE PayInscription(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    year int NOT NULL,
    price DOUBLE(4,2) NOT NULL,
    dateLimit1 TIMESTAMP NOT NULL,
    dateLimit2 TIMESTAMP NOT NULL,
    description VARCHAR(100),
    CONSTRAINT PayInscriptionId_PK PRIMARY KEY (id),
    CONSTRAINT YearUniqueKey UNIQUE (year)
	);    


CREATE TABLE UserPayInscription(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    payInscriptionId INT NOT NULL,
    price DOUBLE(4,2) NOT NULL,
    installment INT,
    installments INT,
    hasPay BOOLEAN,
   	idPayer VARCHAR(30),
   	idTxn VARCHAR(30),
   	emailPayer VARCHAR(30),
   	statusPay VARCHAR(30),
    datePay TIMESTAMP NULL,
    CONSTRAINT UserPayInscriptionId_PK PRIMARY KEY (id),
	CONSTRAINT UserPayInscriptionId_Account_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT PayInscriptionId_FK FOREIGN KEY (payInscriptionId) REFERENCES PayInscription(id),
	CONSTRAINT IdTxnUniqueKey UNIQUE (idTxn)
	);   	


-- Insert Account Types:
insert into AccountType values (1, 'No', 'No tiene que pagar', 100);
insert into AccountType values (2, 'Adulto', 'Tarifa adulta', 0);
insert into AccountType values (3, 'Juvenil', 'Tarifa juvenil', 50);
insert into AccountType values (4, 'Jubilado', 'Tarifa adulta', 50);


-- Insert Method Payment:
insert into MethodPayment values (1, 'No', 'No tiene que pagar');
insert into MethodPayment values (2, 'Efectivo', 'Pago en efectivo');
insert into MethodPayment values (3, 'Domiciliado', 'Domiciliado');
insert into MethodPayment values (4, 'Paypal', 'Paypal');	
	

-- Insert Account:
insert into Account values 
(1, 'user', '12345678A', 'CuacFM', 'user', 'user@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(2, 'admin', '12345678B', 'CuacFM', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 1, 1, 1, false, null, true, '', '', 'ROLE_ADMIN');

insert into Account values 
(3, 'trainer', '12345678C', 'CuacFM', 'trainer', 'trainer@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 1, 1, 1, false, null, true, '', '', 'ROLE_TRAINER');

insert into Account values 
(4, 'Pablo Grela', '12345678D', 'CuacFM', 'pablo', 'pablo@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 3, 3, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(5, 'Manuel Fernandez', '12345678E', 'CuacFM', 'manu', 'manu@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(6, 'Lorena Borrazás', '12345678F', 'CuacFM', 'lore', 'lore@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(7, 'Lorena Fernandez', '12345678Z', 'CuacFM', 'loref', 'loref@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');
	
insert into Account values 
(8, 'Manuel Borrazás', '12345678P', 'CuacFM', 'manub', 'manuf@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(9, 'Pablo Martínez Pérez', '12347678P', 'CuacFM', 'pmp', 'pmp@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');


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