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

-- Migrador de base de datos de Radioco


-- Migrar generos
INSERT INTO members.ProgramCategory
(name, description)
select distinct(category), category from  bdradioco.programmes_programme order by category;



-- Migrar usuarios
INSERT INTO members.Account 
(id, name, dni, address, login, 
email, password, mobile, 
accountTypeId, installments, student, emitprogram, active, observations, role, dateCreate)

SELECT id, CONCAT(first_name, ' ', last_name), CONCAT('dni', id),  'A Coruña', username, 
(case when (email = '') then CONCAT(username, '@cuacfm.es') else email end) as email, password, 666666666, 
2, 1, 0, 0, is_active,'Usuario migrado, se deberia completar los datos', 'ROLE_USER', date_joined
FROM bdradioco.auth_user WHERE id>3 and first_name != '';



-- Migrar programas      
INSERT INTO members.program
(id, name, description, periodicity, duration, accountPayer, programType, programThematic, 
ProgramCategory, programLanguage, email, twitter, facebook, podcast, web, active, dateCreate, dateDown)

SELECT id, name, SUBSTRING(synopsis, 1, 5000),  1, _runtime, null,	7, 8, (select id from members.ProgramCategory where name=p.category),	
(case when (language = 'es') then 1 else 2 end) as programLanguage,	'',	'',	'',	'',	'',	
(case when (end_date is null) then true else false end) as active, start_date, end_date
FROM bdradioco.programmes_programme p;



-- Migrar relacion entre usuarios y programas
INSERT INTO members.userprograms
(id, accountId, programId)

SELECT id, person_id, programme_id 
FROM bdradioco.programmes_role p where id<=(select MAX(id) from members.program);
		