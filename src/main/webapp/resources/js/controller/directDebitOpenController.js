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
membersApp.controller('DirectDebitOpenController', [ '$scope', '$rootScope', 'DirectDebitService', function($scope, $rootScope, DirectDebitService) {
	$scope.directDebits = [];
	$scope.message = '';

	var self = this;
	self.markBankDeposit = markBankDeposit;
	self.cancelBankDeposit = cancelBankDeposit;

	fetchAllDirectDebitsOpen();

	function fetchAllDirectDebitsOpen() {
		DirectDebitService.fetchAllDirectDebitsOpen().then(function(data) {
			$scope.directDebits = data;
			$scope.totalItems = $scope.directDebits.length;
		}, function(errResponse) {
			console.error('Error while fetch directDebits');
		});
	}

	function markBankDeposit(id) {
		DirectDebitService.markBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsOpen();
			showModal(modal);
			//Refresh events
			$rootScope.$emit("fetchAllEvents", {});
		}, function(errResponse) {
			console.error('Error while mark Bank Deposit directDebit');
		});
	}

	function cancelBankDeposit(id) {
		DirectDebitService.cancelBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsOpen();
			showModal(modal);
			//Refresh events
			$rootScope.$emit("fetchAllEvents", {});			
		}, function(errResponse) {
			console.error('Error while cancel Bank Deposit directDebit');
		});
	}

	$scope.childmethod = function() {
		$rootScope.$emit("CallParentMethod", {});
	}

} ]);
