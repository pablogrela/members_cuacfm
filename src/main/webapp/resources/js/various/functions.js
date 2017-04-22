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

// Show and evaluate function
// Set application language
var language = window.navigator.language || navigator.browserLanguage;

function showModal(modal) {
	$(document).ready(function() {
		$(modal).modal('show');
	});
}

// Show and evaluate function
function evaluateModal2(evaluateModal) {
	$(document).ready(function() {
		$(evaluateModal).modal('show');
	});
}

// Show and evaluate function
function evaluateModal(form, evaluateModal, accept) {
	$(document).ready(function() {
		$(evaluateModal).modal('show');
		$(accept).click(function(evento) {
			form.submit()
		});
	});
}

// Change check checkbox
function switchCheckbox(checkbox1, checkbox2) {
	if (document.getElementById(checkbox1).checked) {
		document.getElementById(checkbox2).checked = false;
		document.getElementById(checkbox2).required = false;
	} else {
		document.getElementById(checkbox2).checked = true;
		document.getElementById(checkbox2).required = false;
	}
}

// Check required checkbox
function validateCheckbox(check1, check2) {

	if (document.getElementById(check1 + 'True').checked || document.getElementById(check1 + 'False').checked) {
		document.getElementById(check1 + 'True').required = false;
	}
	if (document.getElementById(check2 + 'True').checked || document.getElementById(check2 + 'False').checked) {
		document.getElementById(check2 + 'True').required = false;
	}
}

// lock and unlock camp depends on check
function unlockText(checkbox, camp) {
	if (document.getElementById(checkbox) != null && document.getElementById(camp) != null) {
		if (document.getElementById(checkbox).checked) {
			document.getElementById(camp).disabled = false;
		} else {
			document.getElementById(camp).disabled = true;
		}
	}
}

// lock and unlock camp depends on check, and visibility div
function unlockText2(checkbox, divVisible, camp1, camp2) {
	if (document.getElementById(checkbox) != null && document.getElementById(camp1) != null && document.getElementById(camp2) != null) {
		if (document.getElementById(checkbox).checked) {
			document.getElementById(camp1).disabled = false;
			document.getElementById(camp2).disabled = false;
			document.getElementById(divVisible).style.display = 'block';
		} else {
			document.getElementById(camp1).disabled = true;
			document.getElementById(camp2).disabled = true;
			document.getElementById(divVisible).style.display = 'none';
		}
	}
}

// Unlock Password to validate email and new password
function unlockPassword() {
	if (document.getElementById('onEmail') != null && document.getElementById('onPassword') != null) {

		// Password
		if (document.getElementById('onEmail').checked || document.getElementById('onPassword').checked) {
			document.getElementById('password').disabled = false;
			document.getElementById('password').requered = true;
			document.getElementById('visiblePassword').style.display = 'block';
		} else {
			document.getElementById('password').disabled = true;
			document.getElementById('visiblePassword').style.display = 'none';
		}

		// Email
		if (document.getElementById('onEmail').checked) {
			document.getElementById('email').disabled = false;
		} else {
			document.getElementById('email').disabled = true;
		}

		// Password
		if (document.getElementById('onPassword').checked) {
			document.getElementById('newPassword').disabled = false;
			document.getElementById('rePassword').disabled = false;
			document.getElementById('visibleRePassword').style.display = 'block';
		} else {
			document.getElementById('newPassword').disabled = true;
			document.getElementById('rePassword').disabled = true;
			document.getElementById('visibleRePassword').style.display = 'none';
		}
	}
}

// Return previous page
function previousPage() {
	window.location = document.referrer;
}

// Function to zoom
function toggleFullscreen(elem) {
	elem = elem || document.documentElement;
	if (!document.fullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement) {
		if (elem.requestFullscreen) {
			elem.requestFullscreen();
		} else if (elem.msRequestFullscreen) {
			elem.msRequestFullscreen();
		} else if (elem.mozRequestFullScreen) {
			elem.mozRequestFullScreen();
		} else if (elem.webkitRequestFullscreen) {
			elem.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
		}
	} else {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}
	}
}

// Function load photos
function loadInputPhotos() {
	$("#photos").fileinput({
		language : language,
		uploadUrl : '#', // you must set a valid URL here else you will get an error
		// allowedFileExtensions: ['jpg', 'png', 'gif'],
		// overwriteInitial: false,
		// maxFileSize: 1000,
		maxFilesNum : 10,
		showUpload : false,
		// showUploadedThumbs: false,
		// showZoom: false,
		// showUploadedThumbs: false,
		// dropZoneEnabled : false,
		// allowedFileTypes: ['image', 'video', 'flash'],
		fileActionSettings : {
			showUpload : false,
		},
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		}
	})
}

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ///////////// LISTENERS //////////////////////////////////////////////////////////////////////////////////
// Listener profile modify
// Se hace asi para que funcionenen las validaciones del submit, en vez de usar un onClick
$(function() {
	$('#profileForm').on('submit', function(e) {
		// Prevent form from submitting (Deshabilita el submit y s ehace manual)
		e.preventDefault();
		updateUser($('#profileForm'), 'password', 'email', 'onEmail', 'newPassword', 'onPassword');
	});
});

// Listener signin modify
// Se hace asi para que funcionenen las validaciones del submit, en vez de usar un onClick
$(function() {
	$('#signinForm').on('submit', function(e) {
		// Prevent form from submitting (Deshabilita el submit y se hace manual)
		e.preventDefault();
		signin($('#signinForm'), 'email', 'password')
	});
});

// Listener restore password
// Se hace asi para que funcionenen las validaciones del submit, en vez de usar un onClick
$(function() {
	$('#restorePasswordForm').on('submit', function(e) {
		// Prevent form from submitting (Deshabilita el submit y se hace manual)
		e.preventDefault();
		restorePassword($('#restorePasswordForm'), 'email')
	});
});

// Listener reset password
// Se hace asi para que funcionenen las validaciones del submit, en vez de usar un onClick
$(function() {
	$('#resetPasswordForm').on('submit', function(e) {
		// Prevent form from submitting (Deshabilita el submit y se hace manual)
		e.preventDefault();
		resetPassword($('#resetPasswordForm'), 'password', 'oobCode')
	});
});

// Listener reset password
// Se hace asi para que funcionenen las validaciones del submit, en vez de usar un onClick
$(function() {
	$('#signupFirebaseManualForm').on('submit', function(e) {
		// Prevent form from submitting (Deshabilita el submit y se hace manual)
		e.preventDefault();
		signupFirebaseManual($('#signupFirebaseManualForm'), 'email', 'password')
	});
});
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ///////////// TEST CODE //////////////////////////////////////////////////////////////////////////////////

// Este codigo esta en pruebas
$(document).ready(
		function() {

			/**
			 * This method will check if the entered value is a valid NIF or NIE Number
			 */
			$.validator.addMethod("nif_or_nie", function(value, element) {
				var nifES = $.validator.methods["nifES"];
				var nieES = $.validator.methods["nieES"];

				return nifES.call(this, value, element) || nieES.call(this, value, element);
			}, "Please enter a valid NIF or NIE Number.");

			$("#form").validate(
					{
						rules : {
							"dni" : {
								// "required" : true,
								"nif_or_nie" : true
							}
						},
						messages : {
							"dni" : {
								// "required" : "Por favor, rellene este
								// campo.",
								"nif_or_nie" : "Por favor, introduce un NIF o NIE valido",
							}
						},
						highlight : function(element) {
							$(element).closest('.form-group').removeClass('has-success').addClass('has-error').end().siblings('.glyphicon')
									.removeClass('glyphicon-ok').addClass('glyphicon-error');
						},
						unhighlight : function(element) {
							$(element).closest('.form-group').removeClass('has-error').addClass('has-success').end().siblings('.glyphicon')
									.removeClass('glyphicon-error').addClass('glyphicon-ok');
						},
						errorElement : 'span',
						errorClass : 'help-block',
						errorPlacement : function(error, element) {
							if (element.length) {
								error.insertAfter(element);
							} else {
								error.insertAfter(element);
							}
						},
					// for demo
					// submitHandler : function(form) {
					// alert("valid form submitted");
					// return false;
					// }

					});
		});

// Parse.initialize("YOUR_APP_ID");
// Parse.serverURL = 'http://YOUR_PARSE_SERVER:1337/parse'
// //var Parse = require('parse');
// var obj = new Parse.Object('GameScore');
// obj.set('score', 1337);
// obj.save().then(function(obj) {
// console.log(obj.toJSON());
// var query = new Parse.Query('GameScore');
// query.get(obj.id).then(function(objAgain) {
// console.log(objAgain.toJSON());
// }, function(err) {
// console.log(err);
// });
// }, function(err) {
// console.log(err);
// });
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
