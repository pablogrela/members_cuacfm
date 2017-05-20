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
membersApp.controller('NotificationController', [ '$scope', 'NotificationService', function($scope, NotificationService) {
	$scope.sortType;
	$scope.search;
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.accounts;
	$scope.accountsFilter = [];
	$scope.message;
	$scope.destinataries = 'ALL';

	$scope.fetchAllUsers = function() {
		NotificationService.fetchAllUsers().then(function(data) {
			$scope.accounts = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.notification = function(destinataries, title, body) {
		if (title != null && !jQuery.isEmptyObject(title) && body != null && !jQuery.isEmptyObject(body)) {
			NotificationService.notification(destinataries, $scope.accountsFilter, title, body).then(function(data) {
				$scope.message = data;
				$scope.title = '';
				$scope.body = '';
				$scope.accountsFilter = [];
				$scope.fetchAllUsers();
			}, function(errorResponse) {
				console.error('Error while notification account', errorResponse);
			});
		}
	}

	function contains(value) {
		for (var i = 0; i < $scope.accounts.length; i++) {
			if ($scope.accounts[i].fullName.toUpperCase() === value.toUpperCase()) {
				return $scope.accounts[i];
			}
			if ($scope.accounts[i].email.toUpperCase() === value.toUpperCase()) {
				return $scope.accounts[i];
			}
		}
		return null;
	}

	$scope.addAccount = function(value) {
		if (value != null && !jQuery.isEmptyObject(value)) {
			var account;
			if ($scope.accounts.indexOf(value) > -1) {
				account = value;
			} else {
				account = contains(value);
			}

			if (account != null) {
				$scope.accountsFilter.push(account);
				var index = $scope.accounts.indexOf(account);
				if (index > -1) {
					$scope.accounts.splice(index, 1);
				}
				$scope.selected = '';
			}
		}
	}

	$scope.removeAccount = function(account) {
		if (account != null && !jQuery.isEmptyObject(account) && $scope.accountsFilter.indexOf(account) > -1) {
			$scope.accounts.push(account);
			var index = $scope.accountsFilter.indexOf(account);
			if (index > -1) {
				$scope.accountsFilter.splice(index, 1);
			}
		}
	}

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.changeDestinataries = function(aux) {
		$scope.destinataries = aux;
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

} ]);