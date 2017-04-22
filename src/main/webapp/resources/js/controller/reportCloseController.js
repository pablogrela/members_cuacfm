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
membersApp.controller('ReportController', [ '$scope', 'ReportService', function($scope, ReportService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account = '';
	$scope.accounts = '';
	$scope.report = '';
	$scope.reports = '';
	$scope.message = '';

	var self = this;
	self.reportUp = reportUp;
	self.infoDelete = infoDelete;
	self.infoAccount = infoAccount;
	self.infoAccounts = infoAccounts;
	self.infoProgram = infoProgram;
	self.infoReport = infoReport;

	fetchAllReportsClose();

	function fetchAllReportsClose() {
		ReportService.fetchAllReportsClose().then(function(data) {
			$scope.reports = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	function reportUp(id) {
		ReportService.reportUp(id).then(function(data) {
			$scope.message = data;
			fetchAllReportsClose();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Up Report', errorResponse);
		});
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	$scope.dirt_average = function() {
		var total = 0;
		for (var i = 0; i < $scope.reportsFilter.length; i++) {
			var report = $scope.reportsFilter[i];
			total += report.dirt;
		}
		return total / $scope.reportsFilter.length;
	}

	$scope.tidy_average = function() {
		var total = 0;
		for (var i = 0; i < $scope.reportsFilter.length; i++) {
			var report = $scope.reportsFilter[i];
			total += report.tidy;
		}
		return total / $scope.reportsFilter.length;
	}

	$scope.configuration_average = function() {
		var total = 0;
		for (var i = 0; i < $scope.reportsFilter.length; i++) {
			var report = $scope.reportsFilter[i];
			total += report.configuration;
		}
		return round(total / $scope.reportsFilter.length, 1);
	}

	function infoAccount(aux) {
		$scope.account = aux;
	}

	function infoAccounts(aux) {
		$scope.accounts = aux;
	}

	function infoReport(aux) {
		// Reset carousel to first image
		// $('#carousel-reports').carousel(0);
		$('#data-slide-0').attr('class', 'active');
		$('#data-slide-1').attr('class', '');
		$('#image-index-0').attr('class', 'item active');
		$('#image-index-1').attr('class', 'item');
		$scope.report = aux;
	}

	function infoProgram(aux) {
		$scope.program = aux;
	}

} ]);
