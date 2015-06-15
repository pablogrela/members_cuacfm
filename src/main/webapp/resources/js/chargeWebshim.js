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
