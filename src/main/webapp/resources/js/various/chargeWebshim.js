/*
 * Copyright © 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* ========================================================================
 * Charge Options of Webshim
 * ========================================================================

 * ======================================================================== */

webshim.setOptions('forms', {
	replaceValidationUI : true,
	lazyCustomMessages : true,
	addValidators : true,
	overrideMessages : true,
	// Replaces the datalist to make it look the same in all browsers
	customDatalist : "auto",
	list : {
		"filter" : "*",
		"multiple" : false,
		"focus" : false,
		"highlight" : false,
		"valueCompletion" : false,
		"inlineValue" : false,
		"noHtmlEscape" : false,
		"popover" : {
			"constrainWidth" : true
		}
	},
	iVal : {
		sel : '.ws-validate',
		// hide error bubble
		handleBubble : 'hide',

		// add bootstrap specific classes
		errorMessageClass : 'help-block',
		successWrapperClass : 'has-success',
		errorWrapperClass : 'has-error',

		// add config to find right wrapper
		fieldWrapper : '.form-group'
	}
});

var ln = x = window.navigator.language || navigator.browserLanguage;
if (ln == 'gl') {
	$(document).ready(function() {
		webshims.activeLang('gl');
	});
}
webshim.polyfill('forms forms-ext');
