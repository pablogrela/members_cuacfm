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
membersApp.controller('DirectDebitAdminRemittanceController', [ '$scope', 'DirectDebitAdminService', function($scope, DirectDebitAdminService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.directDebits = [];
	$scope.account = '';
	$scope.message = '';

	var self = this;
	self.payBankDeposit = payBankDeposit;
	self.markBankDeposit = markBankDeposit;
	self.cancelBankDeposit = cancelBankDeposit;
	self.cancel = cancel;
	self.returnBill = returnBill;
	self.cash = cash;
	self.infoAccount = infoAccount;

	fetchAllDirectDebitsRemittance();

	function fetchAllDirectDebitsRemittance() {
		DirectDebitAdminService.fetchAllDirectDebitsRemittance().then(function(data) {
			$scope.directDebits = data;
		}, function(errResponse) {
			console.error('Error while fetch directDebits');
		});
	}

	function markBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while mark BankDeposit directDebit');
		});
	}

	function cancelBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while cancel BankDeposit directDebit');
		});
	}

	function payBankDeposit(id) {
		DirectDebitAdminService.bankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while pay BankDeposit directDebit');
		});
	}

	function cancel(id) {
		DirectDebitAdminService.cancel(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while cancel directDebit');
		});
	}

	function returnBill(id) {
		DirectDebitAdminService.returnBill(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while returnBill directDebit');
		});
	}

	function cash(id) {
		DirectDebitAdminService.cash(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebitsRemittance();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while returnBill directDebit');
		});
	}

	function infoAccount(aux) {
		$scope.account = aux;
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		// If we don't get strings, just compare by index
		if (v1.type !== 'string' || v2.type !== 'string') {
			return (v1.index < v2.index) ? -1 : 1;
		}

		// Compare strings alphabetically, taking locale into account
		return v1.value.localeCompare(v2.value);
	};
	
} ]);
