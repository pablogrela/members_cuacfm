-- Insert Configuration:
insert into Configuration values (1, 'CuacFM', 'cuacfm@hotmail.com', 981666666, 24, 25, 'Comprométome a coñecer e cumprir a normativa interna da asociación, así coma a asumir a responsabilidade das informacións e opinións que difunda en antena e a facer un bo uso das instalacións e material da asociación.

Se marquei na categoría "soci@", estou a solicitar formalmente o ingreso na asociación cultural  Colectivo de Universitarios ACtivos, cousa que NON ACONTECE, se marquei as opcións "simpatizante" ou "patrocinador web"            ');

	

-- Insert Method Payment:
insert into MethodPayment values (1, 'Exento', false, 'No tiene que pagar');
insert into MethodPayment values (2, 'Efectivo', false, 'Pago en efectivo');
insert into MethodPayment values (3, 'Domiciliado', true, 'Domiciliado');
insert into MethodPayment values (4, 'Paypal', false, 'Paypal');	


-- Insert Account Types:
insert into AccountType values (1, 'Exento', false, 'No tiene que pagar', 100);
insert into AccountType values (2, 'Adulto', false, 'Tarifa adulta', 0);
insert into AccountType values (3, 'Juvenil', false, 'Tarifa juvenil', 50);
insert into AccountType values (4, 'Jubilado', false, 'Tarifa adulta', 50);
insert into AccountType values (5, 'Persona Juridica', true, 'Tarifa adulta', 0);

	

-- Insert Account:
insert into Account values 
(1, 'user', null, '12345678A', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'user', 'user@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(2, 'admin', null, '12345678B', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'admin', 'admin@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 1, 1, 1, false, null, true, '', '', 'ROLE_ADMIN');

insert into Account values 
(3, 'trainer', null, '12345678C', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'trainer', 'trainer@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 1, 1, 1, false, null, true, '', '', 'ROLE_TRAINER');

insert into Account values 
(4, 'Pablo Grela', null, '12345678D', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.grela', 'pablo@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 3, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(5, 'Manuel Fernandez', null, '12345678E', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manuel.fernandez', 'manu@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 4, 1, false, null, true, '', '', 'ROLE_USER');

insert into Account values 
(6, 'Lorena Borrazás', null, '12345678F', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.borrazas', 'lore@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(7, 'Lorena Fernandez', null, '12345678Z', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.fernandez', 'loref@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, null, true, null, '', 'ROLE_USER');
	
insert into Account values 
(8, 'Manuel Borrazás', null, '12345678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manu.borrazas', 'manuf@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 1, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(9, 'Pablo Martínez Pérez', null, '12347678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.martinez.perez', 'pmp@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, null, true, null, '', 'ROLE_USER');

insert into Account values 
(10, 'Conservera Martínez', null, 'A58818501', 'Puerto A Coruña', 'A coruña', 'A coruña', 'ES', 'conservera.martinez', 'conservera.martinez@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 5 , 1, false, null, true, null, '', 'ROLE_USER');
	
	
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