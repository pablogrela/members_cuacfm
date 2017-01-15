-- Datos de ejemplo para usar la aplicación


-- Insert Account:
insert into Account values 
(2, 'Juan B.', null, '12345678A', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'user', 'user@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2, 1, false, false, null, true, '', '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(3, 'Pepe el otro', null, '12345678C', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'trainer', 'trainer@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 1, 1, 1, false, false, null, true, '', '', '', '', 'ROLE_TRAINER', null, null);

insert into Account values 
(4, 'Pablo Grela', null, '12345678D', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.grela', 'pablo@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 3, 1, true, false, null, true, '', '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(5, 'Manuel Fernandez', null, '12345678E', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manuel.fernandez', 'manu@udc.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 4, 1, false, true, null, true, '', '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(6, 'Lorena Borrazás', null, '12345678F', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.borrazas', 'lore@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(7, 'Lorena Fernandez', 'lorenita', '12345678Z', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.fernandez', 'loref@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);
	
insert into Account values 
(8, 'Manuel Borrazás', null, '12345678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manu.borrazas', 'manuf@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(9, 'Pablo Martínez Pérez', 'pau', '12347678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.martinez.perez', 'pmp@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(10, 'Conservera Martínez', null, 'A58818501', 'Puerto A Coruña', 'A coruña', 'A coruña', 'ES', 'conservera.martinez', 'conservera.martinez@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 5 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);
	
insert into Account values 
(11, 'Pera Olart', 'pau', '12347679P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pera', 'pera@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);

insert into Account values 
(12, 'Manolo el del bombo', 'bombo', '22347678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'bombo', 'bombo@udc.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null);
	
	
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
(1, 'Program 1', 'Description of Program 1', 1, 1, 4, 1, 1, 1, 1, '', '', '', '', '', true, null, null);
insert into Program values 
(2, 'Program 2', 'Description of Program 2', 1, 1, 4, 1, 1, 1, 1, '', '', '', '', '', true, null, null);
insert into Program values 
(3, 'Program 3', 'Description of Program 3', 1, 2, 9, 1, 1, 1, 1, '', '', '', '', '', true, null, null);
insert into Program values 
(4, 'Program 4', 'Description of Program 4', 1, 2, 7, 1, 1, 1, 1, '', '', '', '', '', true, null, null);
insert into Program values 
(5, 'Program 5', 'Description of Program 5', 1, 2, 8, 1, 1, 1, 1, '', '', '', '', '', true, null, null);


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
(1, 1, 'Camara', CURRENT_TIMESTAMP - INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a grabar', 'Estudio Cuac', 1.30, 10, 0, true);
insert into Training values 
(2, 2, 'Locución', CURRENT_TIMESTAMP - INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a hablar por radio', 'Estudio Cuac', 2.00, 10, 1, true);
insert into Training values 
(3, 3, 'Redacción', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP + INTERVAL 17 DAY, 'Se enseñara a redactar', 'Estudio Cuac', 1.00, 10, 0, false);
insert into Training values 
(4, 4, 'Audacity', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a hablar por radio', 'Estudio Cuac', 2.10, 10, 0, false);
insert into Training values 
(5, 1, 'Camara', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a grabar','Estudio Cuac', 1.30, 10, 0, false);
insert into Training values 
(6, 2, 'Locución', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a hablar por radio','Estudio Cuac', 2.00, 10, 0, false);



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
