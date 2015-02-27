//lock and unlock field name
function checkedFunction (form, nameChecked, nameField) {
	if (form.nameChecked.checked == true) {
		form.nameField.disabled = false;
	}
	if (form.nameChecked.checked == false) {
		form.nameField.disabled = true;
	}
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

// lock and unlock field login
function checkedLogin(form) {
	if (form.onLogin.checked == true) {
		form.login.disabled = false;
	}
	if (form.onLogin.checked == false) {
		form.login.disabled = true;
	}
}

// lock and unlock field mail
function checkedEmail(form) {
	if (form.onEmail.checked == true) {
		form.email.disabled = false;
	}
	if (form.onEmail.checked == false) {
		form.email.disabled = true;
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

// hide and show button modify
$(document).ready(
		function() {
			$("#enableModify").click(
					function(evento) {
						if (($("#enableModify").prop("checked"))
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

// hide and show button modify
$(document).ready(
		function() {
			$("#enableModify2").click(
					function(evento) {
						if (($("#enableModify").prop("checked"))
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});

// hide and show button modify
$(document).ready(
		function() {
			$("#enableModify3").click(
					function(evento) {
						if (($("#enableModify").prop("checked"))
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
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
						if (($("#enableModify").prop("checked"))
								|| ($("#enableModify2").prop("checked"))
								|| ($("#enableModify3").prop("checked"))
								|| ($("#enablePasswords").prop("checked"))) {
							$("#visibleModify").css("display", "block");
							$("#visibleModify").css("display", "block");
						} else {
							$("#visibleModify").css("display", "none");
						}
					});
		});
