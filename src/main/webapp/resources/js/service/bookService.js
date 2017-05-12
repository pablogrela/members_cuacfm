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

angular.module('membersApp').factory('BookService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/bookList/';
	var REST_SERVICE_URI_USER = '/members/bookUserList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchUserBooks : fetchUserBooks,
		fetchAllBooks : fetchAllBooks,
		fetchAllBooksClose : fetchAllBooksClose,
		bookUp : bookUp,
		bookAccept : bookAccept,
		bookDeny : bookDeny,
		bookAnswer : bookAnswer,
		bookUserAnswer : bookUserAnswer,
	};

	return factory;

	function fetchUserBooks() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI_USER + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Books');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllBooks() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Books');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllBooksClose() {
		var deferred = $q.defer();
		$http.get(REST_SERVICE_URI + "close/" + csrf).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching Books Close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function bookUp(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'bookUp/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while up book');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function bookAccept(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'bookAccept/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while accept book');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function bookDeny(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'bookDeny/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while deny book');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function bookAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI + 'bookAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer book');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function bookUserAnswer(id, answer) {
		var deferred = $q.defer();
		answer = "&answer=" + answer;
		var url = REST_SERVICE_URI_USER + 'bookAnswer/' + id + csrf + answer;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while answer book');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

} ]);
