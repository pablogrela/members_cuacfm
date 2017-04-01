## Members

Web application based on Java 8

<br><br>

## Motivation

**Support** user maintenance, programs and payments from a community radio


<br><br>

## Installation

You need MySQL (5.7) and Apache Tomcat (7, 8, 8.5 or 9)


<br><br>

### Example creation configuration of MySQL

##### Access root, password: root, toor, or the one indicated when MySQL was installed
	mysql -u root -p

##### Create user
	CREATE USER 'myuser'@'localhost' IDENTIFIED BY 'mypassword';

##### Create database
	create database members;

##### Create test database:
	create database membersTest;

##### Give the user access to the database:
	GRANT ALL PRIVILEGES ON members.* to 'myuser'@'localhost' IDENTIFIED BY 'mypassword';

##### Give the user access to the test database:
	GRANT ALL PRIVILEGES ON membersTest.* to 'myuser'@'localhost' IDENTIFIED BY 'mypassword';

##### Import script to create tables and basic data
	mysql -u root -p members < createTables.sql

##### Import script to create example data
	mysql -u root -p members < createDataBase.sql
 
##### Import script to create tables without data
	mysql -u root -p members < createTables.sql


### Generate war using with eclipse or maven
	mvn install



<br><br>

### Deploy application
   Put war in webapps inside tomcat´s home and start tomcat with bin/startup.sh(linux) or bin/startup.bat(windows)



<br><br>

### Maven goals

##### Usar install con skip test por si falla algun test
	install -skip test

##### To run the site, which shows the information of the dependencies and licenses
	site
To view: within the target enter the site and open the index

##### To run the coverage where the code coverage is displayed
	cobertura:cobertura
To view: within the target enter the site / coverage and open the index
	
##### To run mycilla and add the licenses to files that do not have it
	license:format



<br><br>

## Configurations

The configuration is fully customizable.

If you change the **pathConfig** property inside **config.properties**, it will search if there is that path and each of the necessary files, if they are not found there will be searched within the war.
If **config.properties** exists in the indicated path, all that exists in the original **config.properties** will be overwritten with the value of the external **config.properties**.


The necessary files for configuration are the following:

- The **bankRemittance.properties** file contains all the necessary variables for bank remittances.
- The **config.properties** file contains all the necessary variables for the backend part(paths, bankRemittance).
- The **firebaseWeb.properties** ile contains the configuration of Firebase´s web.
- The **hibernate.properties** file contains all the variables needed for database connection.
- The **hibernateTest.properties** file contains all the variables needed for database test connection.
- The **log4.properties** file contains the configuration of the logs hibernate.
- The **logback.xml** file contains the configuration of the logs. Change the variable **<file>C** to change the destination file of the logs, example:
 	<file>/var/lib/cuacfm-members/logs/members.log</file>
- The **messages.properties** file contains the messages of view application, by default use inside messages.
	- The messages of angular, datables or another javascript is not customizable, outside war.
- The **paypal.properties** file contains all the necessary variables for paypal.
- The **recaptcha.properties** file contains all the necessary variables for recaptcha.

- The **members-firebase-adminsdk.json** file contains the configuration of Firebase´s backend.



<br><br>

### Firebase

- Access to Firebase in:
	https://console.firebase.google.com/

- Create a new project and access to your application

- Configure web to connect to firebase, in the file **firebaseWeb.properties**
	Get values in web configuration on the firebase console.
	
- Configure java to connect to firebase, in the file **members-firebase-adminsdk.json**
	Get the json file on the firebase console inside the java sdk admin

- Go to Authentication

	- In Login Method
		Enable Email/password and google sign in
		Add domain, it is necessary 
		In advanced options, verify that only one account is used per email
	
	- Edit action url on firebase console
		Configure this variable in console Firebase (Authentication -> Email templates -> Edit -> Action URL)
		- Example for localhost:
			http://localhost:8080/members/signin/resetPassword
	
	- Url to register manual a email in firebase (example for localhost):
		http://localhost:8080/members/signup/signupFirebaseManual?email=user@udc.es&token=123
		- Email has to be registered on firebase
	    - The token has to correspond to that user and is of only one use
	
	- Configure another email templates, for example reset password, verify email or change email. You can also change the domain.


<br><br>

### Recaptcha

- Access to Recaptcha in:
	https://www.google.com/recaptcha/
	
- Get Recaptcha and put label, select Recaptcha v2, put domains (for example localhost, 127.0.0.1), accept terms and press register.
	Invisible Recaptcha is not validate for this application.
	
- Copy **Secret key** and **Site key** and put this values in **recaptcha.properties**




<br><br>

## Some screenshots
### **Home no sign in**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579440/07dde01c-16f6-11e7-8a47-b6477f21f9b5.png "Home no sign in")

### **Sign in**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579433/fb21ff20-16f5-11e7-94d5-a6b17fe159ae.png "Sign in")

### **Home sign in**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579446/1db4d710-16f6-11e7-98f3-1faa7fa0323f.png "Home sign in*")

### **Accounts**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579531/da8219ba-16f7-11e7-95a0-60b333553d4a.png "Accounts")

### **Account info**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579539/f464b3f6-16f7-11e7-831b-cc5d35c722da.png "Account info")

### **Sessions**
![Alt text](https://cloud.githubusercontent.com/assets/11063006/24579585/946b2f6a-16f8-11e7-959b-3d7bf6619068.png "Sessions")



<br><br>

## Contributors

Any help is welcome


<br><br>

## License Apache
 
   Copyright 2015 Pablo Grela Palleiro

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 
<br><br>
 
## Author
**Pablo Grela** - *Initial work* - [pablogrela](https://github.com/pablogrela)

