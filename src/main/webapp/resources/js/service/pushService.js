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
'use strict';

angular.module('membersApp').factory('PushService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = 'push/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchAllUsers : fetchAllUsers,
		push : push
	};

	return factory;

	function fetchAllUsers() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "accountList/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Users');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function push(accounts, title, body) {
		var deferred = $q.defer();
		var data = "&title=" + title + "&body=" + body;
		var url = REST_SERVICE_URI + csrf + data;		
//		JSON.stringify(accounts)
		$http.post(url, accounts).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while push account');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
} ]);
