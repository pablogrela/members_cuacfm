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
 * Initialize Firebase
 * ========================================================================

 * ======================================================================== */

// Properties that must be changed according to mail where firebase is created
var config = {
	apiKey : "AIzaSyCEHGNNH4tMx3wTJcIBCRA0Mv2Y7RPBSdA",
	authDomain : "members-b4393.firebaseapp.com",
	databaseURL : "https://members-b4393.firebaseio.com",
	storageBucket : "members-b4393.appspot.com",
	messagingSenderId : "187088115583"
};
firebase.initializeApp(config);



// Create a hidden input element, and append it to the form:
function addHidden(theForm, key, value) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = key;
    input.value = value;
    theForm.append(input);
}


// Register user in Firebase
// Automatic call in html
function signupFirebase() {
	var emailValue = document.getElementById('email').value;
	var passwordValue = document.getElementById('password').value;
	
	firebase.auth().createUserWithEmailAndPassword(emailValue, passwordValue)
		.then(function() {
			// Redirect to home
			window.location.href="/members/"
		})
		.catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
		  var errorMessage = error.message;
		  //console.log(errorCode, errorMessage);
		  return errorCode;
		});
}


// Register user in Firebase with parameters
function signup(form, email, password) {
	var emailValue = document.getElementById(email).value;
	var passwordValue = document.getElementById(password).value;
	
	firebase.auth().createUserWithEmailAndPassword(emailValue, passwordValue)
		.then(function() {
			// Redirect to home
			window.location.href="/members/"
			// form.submit()
		})
		.catch(function(error) {
		  // Handle Errors here.
		  var errorCode = error.code;
		  var errorMessage = error.message;
		  return errorCode;
		});
}


// Authentication user in Firebase with parameters
function signin(form, email, password) {
	var emailValue = document.getElementById(email).value;
	var passwordValue = document.getElementById(password).value;
	
	firebase.auth().signInWithEmailAndPassword(emailValue, passwordValue)
		.then(function() {
			form.unbind().submit();
		})
		.catch(function(error) {
		  addHidden(form, 'error', error.code);
          form.unbind().submit();
		});
}


// Authentication user in Firebase with Google
// No se usa
function signinGoogle(form, email, password) {
	// Using a redirect.
	firebase.auth().getRedirectResult().then(function(result) {
	  if (result.credential) {
	    // This gives you a Google Access Token.
	    var token = result.credential.accessToken;
	    // form.submit();
	  }
	  var user = result.user;
	});

	// Start a sign in process for an unauthenticated user.
	var provider = new firebase.auth.GoogleAuthProvider().then(function() {
		firebase.auth().onAuthStateChanged(function(user) {
			if (user) {
				document.getElementById('email').value = user.email;
			}
			form.submit();
		});
	}, function(error) {
		addHidden(form, 'error', error.code);
		form.submit();
	});
	
	provider.addScope('profile');
	provider.addScope('email');
	firebase.auth().signInWithRedirect(provider);	
}


// Authentication user in Firebase using PopUp with Google
function signinGooglePopup(form) {
	// Using a popup.
	var provider = new firebase.auth.GoogleAuthProvider();
	provider.addScope('profile');
	provider.addScope('email');
	firebase.auth().signInWithPopup(provider).then(function(result) {
		 // This gives you a Google Access Token.
		 // var token = result.credential.accessToken;
		 
		 // The signed-in user info.
		 var user = result.user;
		 document.getElementById('email').value = user.email;
		 form.submit();
	})	
	.catch(function(error) {
		 form.submit();
	});
}

// Validate if a user is authenticated
function authenticationValidate(){
	firebase.auth().onAuthStateChanged(function(user) {
		if (user) {
			// User is signed in.
			alert('autenticado');
		} else {
			// No user is signed in.
			alert('no autenticado');
		}
	});
}

// signOut Firebase and Members
function signOut() {
	firebase.auth().signOut().then(function() {
		$('#form').submit();
	}, function(error) {
	  // An error happened.
	});
}

// Update Email
function updateEmail(form, email) {
	var user = firebase.auth().currentUser;
	
	user.updateEmail(email).then(function() {
		form.submit();
	}, function(error) {
		addHidden(form, 'error', error.code);
		form.submit();
	});
}


// Update password
function updatePassword(form, password) {
	var user = firebase.auth().currentUser;
	
	user.updatePassword(password).then(function() {
		form.submit();
	}, function(error) {
		addHidden(form, 'error', error.code);
		form.submit();
	});
}

// Update password and email to user
function updateUser(form, password, email, onEmail, newPassword, onPassword) {
	
	// Checkeds
	if(document.getElementById(onEmail).checked || document.getElementById(onPassword).checked){
		
		var emailValue = document.getElementById(email).value;
		var newPasswordValue = document.getElementById(newPassword).value;
		var passwordValue = document.getElementById(password).value;
		var user = firebase.auth().currentUser;
		
		var credential = firebase.auth.EmailAuthProvider.credential(
			    user.email, 
			    passwordValue
			);
		
		// Reauthenticate
		user.reauthenticate(credential).then(function() {	
			
			// UpdateEmail
			if (document.getElementById(onEmail).checked) {	
				user.updateEmail(emailValue).then(function() {
					form.unbind().submit();
				}, function(error) {
					addHidden(form, 'attribute', "email");
					addHidden(form, 'error', error.code);
					// console.log(error.message);
					form.unbind().submit();
				});
			}
			
			// UpdatePassword
			if (document.getElementById(onPassword).checked) {				
				user.updatePassword(newPasswordValue).then(function() {
					form.unbind().submit();
				}, function(error) {
					addHidden(form, 'attribute', "password");
					addHidden(form, 'error', error.code);
					// console.log(error.message);
					form.unbind().submit();
				});
			}
		
		}, function(error) {
			addHidden(form, 'attribute', "password");
			addHidden(form, 'error', error.code);
			form.unbind().submit();
		});
	} else {
		form.unbind().submit();
	}
}

// Restore password
function restorePassword(form, email) {
	var emailValue = document.getElementById(email).value;
	var auth = firebase.auth();
	
	auth.sendPasswordResetEmail(emailValue).then(function() {
			form.unbind().submit();
		}, function(error) {
			// console.log(error);
			addHidden(form, 'error', error.code);
	        form.unbind().submit();
		});
}


// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ///////////// FIREBASE ADMIN /////// Required Node.js /////////////////////////////////////////////////////

// Firebase Admin, only with node.js
// var admin = require("firebase-admin");
// admin.initializeApp({
// credential: admin.credential.cert("members-firebase-adminsdk.json.json"),
// databaseURL: "https://members-b4393.firebaseio.com"
// });


// Change state of user
// True to Disable
// False to Enabe
function changeStateUser(email, state) {
	admin.auth().getUserByEmail(email)
	.then(function(userRecord) {
	  // See the UserRecord reference doc for the contents of userRecord.
	  // console.log("Successfully fetched user data:", userRecord.toJSON());
	  
	  userRecord.updateProfile({
		  disabled: state
		}).then(function() {
			 console.log("sucefull:");
		}, function(error) {
			 console.log("Error :", error);
		});
	  
	})
	.catch(function(error) {
	  console.log("Error fetching user data:", error);
	});
}

// Get User of Firebase
function getUser(email) {
	admin.auth().getUserByEmail(email)
	.then(function(userRecord) {
		// See the UserRecord reference doc for the contents of userRecord.
		console.log("Successfully fetched user data:", userRecord.toJSON());
	})
	.catch(function(error) {
		console.log("Error fetching user data:", error);
	});
		
	var name, email, photoUrl, uid;
	if (userRecord != null) {
	  name = user.displayName;
	  email = user.email;
	  photoUrl = user.photoURL;
	  // The user's ID, unique to the Firebase project. Do NOT use this value to authenticate with your backend server, if you have one. Use
		// User.getToken() instead.
	  uid = user.uid;  
	}
}
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
