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
membersApp.controller('AccountController', [ '$scope', 'AccountService', function($scope, AccountService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account = '';
	$scope.accounts = [];
	$scope.message = '';

	var self = this;
	self.unsubscribe = unsubscribe;
	self.subscribe = subscribe;
	self.infoAccount = infoAccount;

	fetchAllUsers();

	function fetchAllUsers() {
		AccountService.fetchAllUsers().then(function(data) {
			$scope.accounts = data;
		}, function(errResponse) {
			console.error('Error while fetching Users');
		});
	}

	function unsubscribe(id, email) {
		AccountService.unsubscribe(id).then(function(data) {
			$scope.message = data;
			fetchAllUsers();
			// Disable
			// disableUser(email)
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while unsubscribe User');
		});
	}

	function subscribe(id) {
		AccountService.subscribe(id).then(function(data) {
			$scope.message = data;
			fetchAllUsers();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while subscribe User');
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
