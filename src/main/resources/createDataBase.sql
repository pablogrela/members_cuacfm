--
-- Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- 


-- INFO English
 -- Example data to use the application
 -- To import the tables to the database, it must exist
 -- Can be done by command line or with the workbench


-- INFO Español
 -- Datos de ejemplo para usar la aplicación
 -- Para importar la tablas a la base de datos, esta debe existir
 -- Se puede hacer por linea de comandos o con el workbench


use members;


-- Insert Account:
insert into Account values 
(2, 'Juan', 'B', null, '84364835F', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'user', 'user@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2, 1, false, false, null, true, '', '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(3, 'Pepe', 'el otro', null, '92053447H', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'trainer', 'trainer@test.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 1, 1, 1, false, false, null, true, '', '', '', '', 'ROLE_USER', 'ROLE_TRAINER', null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(4, 'Pablo', 'Grela', null, '45093757A', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.grela', 'pablo@test.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 3, 1, true, false, null, true, '', '', '', '', 'ROLE_USER', 'ROLE_REPORT, ROLE_BOOK', null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(5, 'Manuel', 'Fernández', null, '29244308S', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manuel.fernandez', 'manu@test.es','e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 4, 1, false, true, null, true, '', '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(6, 'Lorena', 'Borrazás', null, '27069088C', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.borrazas', 'lore@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(7, 'Lorena', 'Fernández', 'lorenita', '10147938Q', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'lore.fernandez', 'loref@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);
	
insert into Account values 
(8, 'Manuel', 'Borrazás', null, '01635941C', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'manu.borrazas', 'manuf@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(9, 'Pablo', 'Martínez Pérez', 'pau', '45440822K', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pablo.martinez.perez', 'pmp@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(10, 'Conservera Martínez', '', null, '38959964B', 'Puerto A Coruña', 'A coruña', 'A coruña', 'ES', 'conservera.martinez', 'conservera.martinez@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 5 , 1, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);
	
insert into Account values 
(11, 'Pera', 'Olart', 'pera', '95252576P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'pera', 'pera@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);

insert into Account values 
(12, 'Manolo', 'el del bombo', 'bombo', '22347678P', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'bombo', 'bombo@test.es', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 3, 2 , 2, false, false, null, true, null, '', '', '', 'ROLE_USER', null, null, CURRENT_TIMESTAMP, null, null);
	
insert into Account values 
(13, 'Pablo', 'Grela', null, '5338884J', 'CuacFM', 'A coruña', 'A coruña', 'ES', 'grela', 'pablo.grela.palleiro@gmail.com', 'e496b021d9b009464b104f43e4669c6dd6ecdf00226aba628efbf72e2d68d96115de602b85749e72', 
	981666666, 666666666, 2, 2, 1, false, false, null, true, '', '', '', '', 'ROLE_USER', 'ROLE_REPORT, ROLE_BOOK', null, CURRENT_TIMESTAMP, null, null);	
	
	
	
insert into BankAccount values 	
(1, 4, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '4_12345678D_1', "2015-04-10 11:00", "2015-04-10 11:00", true);		
insert into BankAccount values 	
(2, 8, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '8_12345678P_1', "2015-04-10 11:00", "2015-04-10 11:00", true);	
insert into BankAccount values 	
(3, 9, 'Santander', 'BSCHESMMXXX', 'ES7620770024003102575766', '9_12347678D_1', "2015-04-10 11:00", "2015-04-10 11:00", false);
insert into BankAccount values 	
(4, 9, 'A Banca', 'BancaMMXXX', 'ES7620770024003102575766', '9_12347678D_2', "2015-04-10 11:00", "2015-04-10 11:00", true);	


	

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
insert into UserPrograms values (9, 13, 5);



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
(3, 3, 'Redacción', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP + INTERVAL 17 DAY, 'Se enseñara a redactar', 'Estudio Cuac', 1.00, 10, 1, false);
insert into Training values 
(4, 4, 'Audacity', CURRENT_TIMESTAMP + INTERVAL 15 DAY, CURRENT_TIMESTAMP - INTERVAL 17 DAY, 'Se enseñara a hablar por radio', 'Estudio Cuac', 2.10, 10, 1, false);
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



-- Insert Elements:
insert into Element values 
(1, "Grabadora digital", "Grabadora digital", true, false, CURRENT_TIMESTAMP);
insert into Element values 
(2, "Estudio CuacFM", "Estudio de CuacFM", true, true, CURRENT_TIMESTAMP);
insert into Element values 
(3, "Grabadora analogica", "Grabadora analogica, se ha estropeado", false, false, CURRENT_TIMESTAMP);

