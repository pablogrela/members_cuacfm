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
membersApp.controller('ProgramController', [ '$scope', 'ProgramService', function($scope, ProgramService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account = '';
	$scope.accounts = '';
	$scope.program = '';
	$scope.programs = [];
	$scope.message = '';

	var self = this;
	self.programUp = programUp;
	self.programDown = programDown;
	self.programDelete = programDelete;
	self.infoDelete = infoDelete;
	self.infoAccount = infoAccount;
	self.infoAccounts = infoAccounts;
	self.infoProgram = infoProgram;

	fetchAllPrograms();

	function fetchAllPrograms() {
		ProgramService.fetchAllPrograms().then(function(data) {
			$scope.programs = data;
		}, function(errResponse) {
			console.error('Error while fetching Users');
		});
	}

	function programUp(id) {
		ProgramService.programUp(id).then(function(data) {
			$scope.message = data;
			fetchAllPrograms();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while Up program');
		});
	}

	function programDown(id) {
		ProgramService.programDown(id).then(function(data) {
			$scope.message = data;
			fetchAllPrograms();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while Down Program');
		});
	}

	function programDelete(id) {
		ProgramService.programDelete(id).then(function(data) {
			$scope.message = data;
			fetchAllPrograms();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while Delete Program');
		});
	}

	$scope.programDelete = function(id) {
		ProgramService.programDelete(id).then(function(data) {
			$scope.message = data;
			fetchAllPrograms();
			showModal(modal);
		}, function(errResponse) {
			console.error('Error while Delete Program');
		});
	};

	$scope.localeSensitiveComparator = function(v1, v2) {
		// If we don't get strings, just compare by index
		if (v1.type !== 'string' || v2.type !== 'string') {
			return (v1.index < v2.index) ? -1 : 1;
		}

		// Compare strings alphabetically, taking locale into account
		return v1.value.localeCompare(v2.value);
	};
	
	function infoAccount(aux) {
		$scope.account = aux;
	}

	function infoAccounts(aux) {
		$scope.accounts = aux;
	}
	
	function infoProgram(aux) {
		$scope.program = aux;
	}

	function infoDelete(aux) {
		$scope.program = aux;
	}

} ]);
