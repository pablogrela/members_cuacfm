/*
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* ========================================================================
 *  Initialize Firebase
 * ======================================================================== */

//Load configuration
var apiKey = document.getElementById("firebase.web.apiKey").value;
var authDomain = document.getElementById("firebase.web.authDomain").value;
var databaseURL = document.getElementById("firebase.web.databaseURL").value;
var storageBucket = document.getElementById("firebase.web.storageBucket").value;
var messagingSenderId = document.getElementById("firebase.web.messagingSenderId").value;

// Properties that must be changed according to mail where firebase is created
var config = {
	apiKey : apiKey,
	authDomain : authDomain,
	databaseURL : databaseURL,
	storageBucket : storageBucket,
	messagingSenderId : messagingSenderId
};
firebase.initializeApp(config);

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
