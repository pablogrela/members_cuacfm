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

angular.module('membersApp').controller('AccountController',
		[ '$scope', 'AccountService', function($scope, AccountService, DTOptionsBuilder, DTColumnBuilder) {
			var self = this;
			self.accounts = [];
			self.unsubscribe = unsubscribe;
			self.subscribe = subscribe;
			$scope.sortType = ''; // set the default sort type
			$scope.sortReverse = false; // set the default sort order
			$scope.search = ''; // set the default search/filter term

			fetchAllUsers();

			function fetchAllUsers() {
				AccountService.fetchAllUsers().then(function(d) {
					self.accounts = d;
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

		} ]);
