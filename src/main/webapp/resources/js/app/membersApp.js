'use strict';

var App = angular.module('membersApp', [ 'datatables' ]).run(function(DTDefaultOptions) {

	var ln = window.navigator.language || navigator.browserLanguage;
	if (ln == 'gl') {
		DTDefaultOptions.setLanguage({
			sUrl: "resources/json/datatables/Galician.json"
		});

	} else if (ln == 'es') {
		DTDefaultOptions.setLanguage({
			sUrl: "resources/json/datatables/Spanish.json"
		});
	}
	DTDefaultOptions.setDisplayLength(25);

});
