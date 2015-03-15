DROP TABLE Inscription;
DROP TABLE Training;
DROP TABLE TrainingType;
DROP TABLE Account;

CREATE TABLE Account(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    login VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(12) NOT NULL,
    CONSTRAINT AccountId_PK PRIMARY KEY (id),
    CONSTRAINT LoginUniqueKey UNIQUE (login),
    CONSTRAINT EmailUniqueKey UNIQUE (email)
    );


    
DROP TABLE TrainingType;

CREATE TABLE TrainingType(
    id INT NOT NULL auto_increment, 
    name VARCHAR(30) NOT NULL,
    required BOOLEAN,
    description VARCHAR(500),
    place VARCHAR(30),
    duration DECIMAL(3,2),
    CONSTRAINT TrainingId_PK PRIMARY KEY (id),
    CONSTRAINT NameUniqueKey UNIQUE (name)
	);
    
    
DROP TABLE Training;

CREATE TABLE Training(	
    id INT NOT NULL auto_increment, 
    trainingTypeId INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    dateTraining TIMESTAMP NOT NULL,
    dateLimit TIMESTAMP NOT NULL,
    description VARCHAR(500),
    place VARCHAR(30),
    duration DECIMAL(3,2),
    maxPlaces INT NOT NULL,
    countPlaces INT NOT NULL,
    close BOOLEAN,
    CONSTRAINT TrainingId_PK PRIMARY KEY (id),
    CONSTRAINT TrainingeId_FK FOREIGN KEY (trainingTypeId) REFERENCES TrainingType(id)
	);


DROP TABLE Inscription;

CREATE TABLE Inscription(
    id INT NOT NULL auto_increment, 
    accountId INT NOT NULL,
    trainingId INT NOT NULL,
    attend BOOLEAN,
    note VARCHAR(500),
    pass BOOLEAN,
    unsubscribe BOOLEAN,
    CONSTRAINT Inscription_PK PRIMARY KEY (id),
	CONSTRAINT AccountId_FK FOREIGN KEY (accountId) REFERENCES Account(id),
	CONSTRAINT TrainingId_FK FOREIGN KEY (trainingId) REFERENCES Training(id)
    );


-- Insert Users:
insert into Account values 
(1, 'user', 'user', 'user@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_USER');

insert into Account values 
(2, 'admin', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_ADMIN');

insert into Account values 
(3, 'trainer', 'trainer', 'trainer@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_TRAINER');

insert into Account values 
(4, 'pablo', 'pablo', 'pablo@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_USER');

insert into Account values 
(5, 'manu', 'manu', 'manu@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_USER');

insert into Account values 
(6, 'lore', 'lore', 'lore@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 'ROLE_USER');



-- Insert Trainings Type:
insert into TrainingType values 
(1, 'Camara', true, 'Se enseñara a grabar', 'Estudio Cuac', 1.30);
insert into TrainingType values 
(2, 'Locución', true, 'Se enseñara a hablar', 'Estudio Cuac', 2.00);
insert into TrainingType values 
(3, 'Redacción', false, 'Se enseñara a redactar el programa', 'Estudio Cuac', 1);
insert into TrainingType values 
(4, 'Audacity', false, 'Se enseñara a posicionarse en el estudio', 'Estudio Cuac', 2.10);



-- Insert Trainings:
insert into Training values 
(1, 1, 'Camara', "2015-06-10 11:00", "2015-06-08 11:00", 'Se enseñara a grabar', 'Estudio Cuac', 1.30, 10, 0, true);
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