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

angular.module('membersApp').factory('IncidenceService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/incidenceList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchAllIncidences : fetchAllIncidences,
		fetchAllIncidencesClose : fetchAllIncidencesClose,
		incidenceUp : incidenceUp,
		incidenceDown : incidenceDown,
		incidenceAnswer : incidenceAnswer,
	};

	return factory;

	function fetchAllIncidences() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Incidences');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllIncidencesClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "close/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Incidences Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function incidenceUp(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'incidenceUp/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while up incidence');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function incidenceDown(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'incidenceDown/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while down incidence');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function incidenceAnswer(id, answer) {
		var deferred = $q.defer();
		answer =  "&answer=" + answer;
		var url = REST_SERVICE_URI + 'incidenceAnswer/' + id  + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer incidence');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
