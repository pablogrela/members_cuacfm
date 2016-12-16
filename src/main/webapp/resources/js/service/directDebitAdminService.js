/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

'use strict';

angular.module('membersApp').factory('DirectDebitAdminService', [ '$http', '$q', function($http, $q) {

	var REST_SERVICE_URI = '/members/directDebitList/';
	var csrf = '?' + document.getElementById("csrf.parameterName").value + '=' + document.getElementById("csrf.token").value;

	var factory = {
		fetchAllDirectDebits : fetchAllDirectDebits,
		fetchAllDirectDebitsClose : fetchAllDirectDebitsClose,
		fetchAllDirectDebitsRemittance : fetchAllDirectDebitsRemittance,
		payBankDeposit : payBankDeposit,
		markBankDeposit : markBankDeposit,
		cancelBankDeposit : cancelBankDeposit,
		cancel : cancel,
		returnBill : returnBill,
		cash : cash

	};

	return factory;

	function fetchAllDirectDebits() {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + csrf;
		$http.get(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching directDebits');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllDirectDebitsRemittance() {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + "remittance/" + csrf;
		$http.get(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching directDebits');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function fetchAllDirectDebitsClose() {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + "close/" + csrf;
		$http.get(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while fetching directDebits close');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function markBankDeposit(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'markBankDeposit/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while mark bankDeposit directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function cancelBankDeposit(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'cancelBankDeposit/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while cancel bankDeposit directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function payBankDeposit(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'payBankDeposit/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while pay bankDeposit directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
	
	function cancel(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'cancel/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while cancel directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function returnBill(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'returnBill/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while returnBill directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}

	function cash(id) {
		var deferred = $q.defer();
		var url = REST_SERVICE_URI + 'cash/' + id + csrf;
		$http.post(url).then(function(response) {
			deferred.resolve(response.data);
		}, function(errResponse) {
			console.error('Error while cash directDebit');
			deferred.reject(errResponse);
		});
		return deferred.promise;
	}
} ]);
