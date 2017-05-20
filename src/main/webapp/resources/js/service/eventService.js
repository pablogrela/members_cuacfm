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

angular.module('membersApp').factory('EventService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = 'eventList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchAllEvents : fetchAllEvents,
		fetchAllEventsClose : fetchAllEventsClose,
		highlight : highlight,
		remove : remove
	};

	return factory;

	function fetchAllEvents() {

		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Events');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllEventsClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + 'close/' + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Events Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function highlight(id) {
		var deferred = $q.defer();
		var a = REST_SERVICE_URI + 'highlight/' + id + csrf;
		$http.post(a).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while highlight Event');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function remove(id) {
		var deferred = $q.defer();
		var a = REST_SERVICE_URI + 'remove/' + id + csrf;
		$http.post(a).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while remove Event');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
