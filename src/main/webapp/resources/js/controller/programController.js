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
	$scope.totalItems = 0;
	$scope.currentPage = 1;
	$scope.numPerPage = 200;
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
			$scope.totalItems = $scope.programs.length;
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
	
	$scope.paginate = function(value) {
		var begin, end, index;
		begin = ($scope.currentPage - 1) * $scope.numPerPage;
		end = begin + $scope.numPerPage;
		index = $scope.programs.indexOf(value);
		return (begin <= index && index < end);
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
