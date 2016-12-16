'use strict';

var membersApp = angular.module('membersApp', [ 'datatables', 'ui.bootstrap', 'ngResource'  ]).run(function(DTDefaultOptions) {

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

