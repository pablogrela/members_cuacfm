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

membersApp.controller('AccountController', [ '$scope', 'AccountService', function($scope, AccountService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.totalItems = 0;
	$scope.currentPage = 1;
	$scope.numPerPage = 20;
	$scope.accounts = [];

	var self = this;
	self.unsubscribe = unsubscribe;
	self.subscribe = subscribe;
	self.account = account;

	fetchAllUsers();

	function fetchAllUsers() {
		AccountService.fetchAllUsers().then(function(d) {
			$scope.accounts = d;
			$scope.totalItems = $scope.accounts.length;
		}, function(errResponse) {
			console.error('Error while fetching Users');
		});
	}

	function unsubscribe(id) {
		AccountService.unsubscribe(id).then(fetchAllUsers, function(errResponse) {
			console.error('Error while unsubscribe User');
		});
	}

	function subscribe(id) {
		AccountService.subscribe(id).then(fetchAllUsers, function(errResponse) {
			console.error('Error while subscribe User');
		});
	}

	function account(id) {
		AccountService.account(id);
	}

	$scope.paginate = function(value) {
		var begin, end, index;
		begin = ($scope.currentPage - 1) * $scope.numPerPage;
		end = begin + $scope.numPerPage;
		index = $scope.accounts.indexOf(value);
		return (begin <= index && index < end);
	};

} ]);
