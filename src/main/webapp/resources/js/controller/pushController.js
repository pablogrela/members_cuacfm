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
membersApp.controller('PushController', [ '$scope', 'PushService', function($scope, PushService) {
	$scope.sortType;
	$scope.search;
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.accounts;
	$scope.accountsFilter = [];
	$scope.message;

	$scope.fetchAllUsers = function() {
		PushService.fetchAllUsers().then(function(data) {
			$scope.accounts = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.push = function(title, body) {
		if (title != null && !jQuery.isEmptyObject(title) && body != null && !jQuery.isEmptyObject(body)) {
			PushService.push($scope.accountsFilter, title, body).then(function(data) {
				$scope.message = data;
				$scope.title = '';
				$scope.body = '';
				$scope.accountsFilter = [];
			}, function(errorResponse) {
				console.error('Error while push account', errorResponse);
			});
		}
	}

	$scope.addAccount = function(account) {
		if (account != null && !jQuery.isEmptyObject(account)) {
			$scope.accountsFilter.push(account);
			var index = $scope.accounts.indexOf(account);
			if (index > -1) {
				$scope.accounts.splice(index, 1);
			}
			$scope.selected = '';
		}
	}

	$scope.removeAccount = function(account) {
		$scope.accounts.push(account);
		var index = $scope.accountsFilter.indexOf(account);
		if (index > -1) {
			$scope.accountsFilter.splice(index, 1);
		}
	}

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

} ]);