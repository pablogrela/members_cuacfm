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
membersApp.controller('DirectDebitAdminCloseController', [ '$scope', 'DirectDebitAdminService', function($scope, DirectDebitAdminService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.totalItems = 0;
	$scope.currentPage = 1;
	$scope.numPerPage = 20;
	$scope.directDebits = [];
	$scope.account = '';
	$scope.message = '';

	var self = this;
	self.markBankDeposit = markBankDeposit;
	self.cancelBankDeposit = cancelBankDeposit;
	self.payBankDeposit = payBankDeposit;
	self.cancel = cancel;
	self.returnBill = returnBill;
	self.cash = cash;
	self.infoAccount = infoAccount;

	fetchAllDirectDebitsClose();

	function fetchAllDirectDebitsClose() {
		DirectDebitAdminService.fetchAllDirectDebitsClose().then(function(data) {
			$scope.directDebits = data;
			$scope.totalItems = $scope.directDebits.length;
		}, function(errResponse) {
			console.error('Error while fetch directDebits');
		});
	}

	function markBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while mark BankDeposit directDebit');
		});
	}

	function cancelBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while cancel BankDeposit directDebit');
		});
	}

	function payBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while pay BankDeposit directDebit');
		});
	}

	function cancel(id) {
		DirectDebitAdminService.cancel(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while cancel directDebit');
		});
	}

	function returnBill(id) {
		DirectDebitAdminService.returnBill(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while returnBill directDebit');
		});
	}

	function cash(id) {
		DirectDebitAdminService.cash(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsClose();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while returnBill directDebit');
		});
	}

	function infoAccount(aux) {
		$scope.account = aux;
	}

	$scope.paginate = function(value) {
		var begin, end, index;
		begin = ($scope.currentPage - 1) * $scope.numPerPage;
		end = begin + $scope.numPerPage;
		index = $scope.directDebits.indexOf(value);
		return (begin <= index && index < end);
	};

} ]);
