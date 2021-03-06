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
membersApp.controller('DirectDebitController', [ '$scope', 'DirectDebitService', function($scope, DirectDebitService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
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
		}, function(errorResponse) {
			console.error('Error while fetch directDebits', errorResponse);
		});
	}

	function markBankDeposit(id) {
		DirectDebitService.markBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebits();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while mark BankDeposit directDebit', errorResponse);
		});
	}

	function cancelBankDeposit(id) {
		DirectDebitService.cancelBankDeposit(id).then(function(data) {
			$scope.message = data;
			fetchAllDirectDebits();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while cancel BankDeposit directDebit', errorResponse);
		});
	}
	
	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

} ]);
