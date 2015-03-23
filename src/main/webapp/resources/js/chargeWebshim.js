/* ========================================================================
 * Charge Options for webshim
 * ========================================================================

 * ======================================================================== */

webshim.setOptions(
	'forms',{
		replaceValidationUI: true,
		lazyCustomMessages: true, 
		addValidators: true, 
		overrideMessages: true,         
		iVal: {
		    sel: '.ws-validate',
		 	// hide error bubble
		    handleBubble: 'hide', 
		
		    //add bootstrap specific classes
		    errorMessageClass: 'help-block',
		    successWrapperClass: 'has-success',
		    errorWrapperClass: 'has-error',
		
		    //add config to find right wrapper
		    fieldWrapper: '.form-group'
		}	
	}
);
webshim.polyfill('forms forms-ext');