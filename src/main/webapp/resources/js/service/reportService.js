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

angular.module('membersApp').factory('ReportService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/reportList/';
	var REST_SERVICE_URI_USER = '/members/reportUserList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchUserReports : fetchUserReports,
		fetchAllReports : fetchAllReports,
		fetchAllReportsClose : fetchAllReportsClose,
		reportUp : reportUp,
		reportDown : reportDown,
		reportAnswer : reportAnswer,
		reportUserAnswer : reportUserAnswer,
	};

	return factory;

	function fetchUserReports() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI_USER + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reports');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllReports() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reports');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllReportsClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "close/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reports Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reportUp(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'reportUp/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while up report');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reportDown(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'reportDown/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while down report');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reportAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI + 'reportAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer report');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reportUserAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI_USER + 'reportAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer report');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
