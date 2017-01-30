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
'use strict';

angular.module('membersApp').factory('ProgramService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/programList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchAllPrograms : fetchAllPrograms,
		fetchAllProgramsClose : fetchAllProgramsClose,
		programUp : programUp,
		programDown : programDown,
		programDelete : programDelete
	};

	return factory;

	function fetchAllPrograms() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Programs');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllProgramsClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "close/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Programs Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function programUp(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'programUp/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while up program');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function programDown(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'programDown/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while down program');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function programDelete(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'programDelete/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while remove program');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
