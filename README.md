## Members

Web application based on Java 8

## Motivation

**Support** user maintenance, programs and payments from a community radio

## Installation

You need MySQL and tomcat (7, 8, 8.5 or 9)

### Configuration items

#### Main files to modify:
1. messages.properties--> configurable variables


## Some screenshots
![Alt text](https://cloud.githubusercontent.com/assets/11063006/21263537/ad5fcb84-c397-11e6-94aa-caeadaaa2424.png "CUACFM home")
![Alt text](https://cloud.githubusercontent.com/assets/11063006/21263521/93bbf586-c397-11e6-83d7-a2b97f0b3a47.png "Users")
![Alt text](https://cloud.githubusercontent.com/assets/11063006/21263527/9d5f26ee-c397-11e6-8963-398308facea3.png "Info user")

## Contributors

Any help is welcome


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
 
## Author
**Pablo Grela** - *Initial work* - [pablogrela](https://github.com/pablogrela)


## Firebase
- Configure web to connect to firebase, in the file **loadFirebase.js**
	apiKey : "AIzaSyCEHGNNH5tMx3wTJcIBCRA0Mv2Y7RPBSdB",
	authDomain : "members.firebaseapp.com",
	databaseURL : "https://members.firebaseio.com",
	storageBucket : "members.appspot.com",
	messagingSenderId : "187088115584"
	
- Configure java to connect to firebase, in the file **members-firebase-adminsdk.json**
	Get the json file on the firebase console inside the java sdk admin
	Change **urlFirebase** int config.properties (example)
		https://members-b4393.firebaseio.com

- Edit action url on firebase 
	Configure this variable in console Firebase (Authentication -> Email templates -> Edit -> Action URL)
	- Example for localhost:
		http://localhost:8080/members/signin/resetPassword

- Url to register manual a email in firebase (example for localhost):
	http://localhost:8080/members/signup/signupFirebaseManual?email=user@udc.es&token=123
	- Email has to be registered on firebase
    - The token has to correspond to that user and is of only one use


## Configurations
- The **persistence.properties** file contains all the variables needed for database connection
- The **persistenceTest.properties** file contains all the variables needed for database Test connection
- The **config.properties** file contains all the necessary variables for the backend part(paths, bankRemittance)
- The **web.properties** file contains all the necessary variables for the frontend part (paypal, footert)
- The **logback.xml** file contains the configuration of the logs. Change the variable **<file>C** to change the destination file of the logs, example:
 	<file>C:/Users/pablo/members/members.log</file>
