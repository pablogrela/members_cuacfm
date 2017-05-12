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
membersApp.controller('ProgramController', [ '$scope', 'ProgramService', function($scope, ProgramService) {
	$scope.sortType;
	$scope.search;
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.accounts;
	$scope.program;
	$scope.programs;
	$scope.message;

	$scope.fetchAllPrograms = function() {
		ProgramService.fetchAllPrograms().then(function(data) {
			$scope.programs = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.programUp = function(id) {
		ProgramService.programUp(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllPrograms();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Up program', errorResponse);
		});
	}

	$scope.programDown = function(id) {
		ProgramService.programDown(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllPrograms();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Down Program', errorResponse);
		});
	}

	$scope.programDelete = function(id) {
		ProgramService.programDelete(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllPrograms();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Delete Program', errorResponse);
		});
	}

	$scope.programNotification = function(id, title, body) {
		if (title != null && !jQuery.isEmptyObject(title) && body != null && !jQuery.isEmptyObject(body)) {
			ProgramService.programNotification(id, title, body).then(function(data) {
				$scope.message = data;
				$('#close').click();
				$scope.title = '';
				$scope.body = '';
			}, function(errorResponse) {
				console.error('Error while push program', errorResponse);
			});
		}
	}
	
	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.infoAccounts = function(aux) {
		$scope.accounts = aux;
	}

	$scope.infoProgram = function(aux) {
		$scope.program = aux;
	}

	$scope.infoDelete = function(aux) {
		$scope.program = aux;
	}

} ]);
