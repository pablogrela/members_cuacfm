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
function evaluateModal(form, modal, accept) {
	$(document).ready(function() {
		$(modal).modal('show');
		$(accept).click(function(evento) {
			form.submit()
		});
	});
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

//lock and unlock camp depends on check, and visibility div
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
