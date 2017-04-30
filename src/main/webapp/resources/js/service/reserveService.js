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

angular.module('membersApp').factory('ReserveService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/reserveList/';
	var REST_SERVICE_URI_USER = '/members/reserveUserList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchUserReserves : fetchUserReserves,
		fetchAllReserves : fetchAllReserves,
		fetchAllReservesClose : fetchAllReservesClose,
		reserveUp : reserveUp,
		reserveAccept : reserveAccept,
		reserveDeny : reserveDeny,
		reserveAnswer : reserveAnswer,
		reserveUserAnswer : reserveUserAnswer,
	};

	return factory;

	function fetchUserReserves() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI_USER + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reserves');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllReserves() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reserves');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllReservesClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "close/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Reserves Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reserveUp(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'reserveUp/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while up reserve');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reserveAccept(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'reserveAccept/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while accept reserve');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reserveDeny(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'reserveDeny/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while deny reserve');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function reserveAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI + 'reserveAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer reserve');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function reserveUserAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI_USER + 'reserveAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer reserve');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
