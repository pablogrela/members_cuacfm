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
function validateCheckbox() {
	if (document.getElementById('studentTrue').checked || document.getElementById('studentFalse').checked) {
		document.getElementById('studentTrue').required = false;
	}
	if (document.getElementById('emitProgramTrue').checked || document.getElementById('emitProgramFalse').checked) {
		document.getElementById('emitProgramTrue').required = false;
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
//								"required" : true,
								"nif_or_nie" : true
							}
						},
						messages : {
							"dni" : {
//								"required" : "Por favor, rellene este campo.",
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
//						submitHandler : function(form) {
//							alert("valid form submitted");
//							return false;
//						}

					});
		});
