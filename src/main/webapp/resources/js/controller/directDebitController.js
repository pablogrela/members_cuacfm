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
membersApp.controller('DirectDebitController', [ '$scope', 'DirectDebitService', function($scope, DirectDebitService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.totalItems = 0;
	$scope.currentPage = 1;
	$scope.numPerPage = 20;
	$scope.directDebits = [];
	$scope.message = '';

	var self = this;
	self.markBankDeposit = markBankDeposit;
	self.cancelBankDeposit = cancelBankDeposit;

	fetchAllDirectDebits();

	function fetchAllDirectDebits() {
		DirectDebitService.fetchAllDirectDebits().then(function(data) {
			$scope.directDebits = data;
			$scope.totalItems = $scope.directDebits.length;
		}, function(errResponse) {
			console.error('Error while fetch directDebits');
		});
	}

	function markBankDeposit(id) {
		DirectDebitService.markBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebits();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while mark BankDeposit directDebit');
		});
	}

	function cancelBankDeposit(id) {
		DirectDebitService.cancelBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebits();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while cancel BankDeposit directDebit');
		});
	}

	$scope.paginate = function(value) {
		var begin, end, index;
		begin = ($scope.currentPage - 1) * $scope.numPerPage;
		end = begin + $scope.numPerPage;
		index = $scope.accounts.indexOf(value);
		return (begin <= index && index < end);
	};

} ]);
