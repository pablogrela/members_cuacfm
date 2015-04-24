//lock and unlock field name

/*
function checkedFunction (form, checked, field) {
	if (form.checked.checked == true) {
		form.name.disabled = false;
	}
	if (form.checked.checked == false) {
		form.name.disabled = true;
	}

}*/

/*
/Show and evaluate function
function evaluateModal(){ 
	$(document).ready(function(){
		$("#modal").modal('show');
		$("#accept").click (
				function(evento) {
					$("#formEvaluate").submit() 
				});
	}); 
} 
} 
*/

/*
function checkedFunction (checked, field) {
	$(document).ready(function(){
			$(checked).click (
					function(evento) {
						if ($(checked).prop("checked")) {
							$(field).disabled = false;
						} else {
							$(field).disabled = true;
						}
		});
	});
}
*/


//View by defect show message
function evaluateModal5(){ 
    if (confirm('¿Estas seguro de enviar este formulario?')){ 
       document.form.submit() 
    } 
} 

//Show and evaluate function
function evaluateModal(form, modal, accept){ 
	$(document).ready(function(){
		$(modal).modal('show');
		$(accept).click (
				function(evento) {
					$(form).submit() 
				});
	}); 
} 


//lock and unlock field name
function checkedName(form) {
	if (form.onName.checked == true) {
		form.name.disabled = false;
	}
	if (form.onName.checked == false) {
		form.name.disabled = true;
	}
}

//lock and unlock field dni
function checkedDni(form) {
	if (form.onDni.checked == true) {
		form.dni.disabled = false;
	}
	if (form.onDni.checked == false) {
		form.dni.disabled = true;
	}
}

//lock and unlock field address
function checkedAddress(form) {
	if (form.onAddress.checked == true) {
		form.address.disabled = false;
	}
	if (form.onAddress.checked == false) {
		form.address.disabled = true;
	}
}

// lock and unlock field login
function checkedLogin(form) {
	if (form.onLogin.checked == true) {
		form.login.disabled = false;
	}
	if (form.onLogin.checked == false) {
		form.login.disabled = true;
	}
}

// lock and unlock field email
function checkedEmail(form) {
	if (form.onEmail.checked == true) {
		form.email.disabled = false;
	}
	if (form.onEmail.checked == false) {
		form.email.disabled = true;
	}
}

//lock and unlock field phone
function checkedPhone(form) {
	if (form.onPhone.checked == true) {
		form.phone.disabled = false;
	}
	if (form.onPhone.checked == false) {
		form.phone.disabled = true;
	}
}

//lock and unlock field mobile
function checkedMobile(form) {
	if (form.onMobile.checked == true) {
		form.mobile.disabled = false;
	}
	if (form.onMobile.checked == false) {
		form.mobile.disabled = true;
	}
}

//lock and unlock field Student
function checkedStudent(form) {
	if (form.onStudent.checked == true) {
		form.student.disabled = false;
	}
	if (form.onStudent.checked == false) {
		form.student.disabled = true;
	}
}

//lock and unlock field DateBirth
function checkedDateBirth(form) {
	if (form.onDateBirth.checked == true) {
		form.dateBirth.disabled = false;
	}
	if (form.onDateBirth.checked == false) {
		form.dateBirth.disabled = true;
	}
}

// lock and unlock field password and rePassword
function checkedPassword(form) {
	if (form.onPassword.checked == true) {
		form.password.disabled = false;
		form.rePassword.disabled = false;
	}
	if (form.onPassword.checked == false) {
		form.password.disabled = true;
		form.rePassword.disabled = true;
	}
}

//lock and unlock field accountType
function checkedAccountType(form) {
	if (form.onAccountType.checked == true) {
		form.accountType.disabled = false;
	}
	if (form.onAccountType.checked == false) {
		form.accountType.disabled = true;
	}
}

//lock and unlock field methodPayment
function checkedMethodPayment(form) {
	if (form.onMethodPayment.checked == true) {
		form.methodPayment.disabled = false;
	}
	if (form.onMethodPayment.checked == false) {
		form.methodPayment.disabled = true;
	}
}

//lock and unlock field installments
function checkedInstallments(form) {
	if (form.onInstallments.checked == true) {
		form.installments.disabled = false;
	}
	if (form.onInstallments.checked == false) {
		form.installments.disabled = true;
	}
}

//lock and unlock field Observations
function checkedObservations(form) {
	if (form.onObservations.checked == true) {
		form.observations.disabled = false;
	}
	if (form.onObservations.checked == false) {
		form.observations.disabled = true;
	}
}

//lock and unlock field Observations
function checkedRole(form) {
	if (form.onRole.checked == true) {
		form.role.disabled = false;
	}
	if (form.onRole.checked == false) {
		form.role.disabled = true;
	}
}




//////////// SHOW BUTTON MODIFY ////////////////////////////////////////////////////
// hide and show button modify
$(document).ready(
		function() {
			$("#enableModify").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| $("#enableModify2").prop("checked")
								|| $("#enableModify3").prop("checked")
								|| $("#enableModify4").prop("checked")
								|| $("#enableModify5").prop("checked")
								|| $("#enableModify6").prop("checked")
								|| $("#enableModify7").prop("checked")
								|| $("#enableModify8").prop("checked")
								|| $("#enableModify9").prop("checked")
								|| $("#enableModify10").prop("checked")
								|| $("#enableModify11").prop("checked")
								|| $("#enableModify12").prop("checked")
								|| $("#enableModify13").prop("checked")
								|| $("#enableModify14").prop("checked")
								|| $("#enablePasswords").prop("checked")) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});


//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify2").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify3").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify4").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify5").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify6").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify7").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify8").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify9").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify10").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify11").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify12").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify13").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

//hide and show button modify
$(document).ready(
		function() {
			$("#enableModify14").click (
					function(evento) {
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});
// hide and show button modify
// hide and show field rePassword
$(document).ready(
		function() {
			$("#enablePasswords").click(
					function(evento) {
						if ($("#enablePasswords").prop("checked")) {
							$("#visibleRePasswords").css("display", "block");
						} else {
							$("#visibleRePasswords").css("display", "none");
						}
						if ($("#enableModify").prop("checked")
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enableModify4").prop("checked"))
								|| ($("#enableModify5").prop("checked"))
								|| ($("#enableModify6").prop("checked"))
								|| ($("#enableModify7").prop("checked"))
								|| ($("#enableModify8").prop("checked"))
								|| ($("#enableModify9").prop("checked"))
								|| ($("#enableModify10").prop("checked"))
								|| ($("#enableModify11").prop("checked"))
								|| ($("#enableModify12").prop("checked"))
								|| ($("#enableModify13").prop("checked"))
								|| ($("#enableModify14").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});
