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
membersApp.controller('ReportUserController', [ '$scope', 'ReportService', function($scope, ReportService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account = '';
	$scope.accounts = '';
	$scope.report = '';
	$scope.reports = '';
	$scope.program = '';
	$scope.message = '';

	var self = this;
	self.infoAccount = infoAccount;
	self.infoAccounts = infoAccounts;
	self.infoProgram = infoProgram;
	self.infoReport = infoReport;
	self.reportAnswer = reportAnswer;
	self.infoReportAnswer = infoReportAnswer;

	fetchUserReports();

	function fetchUserReports() {
		ReportService.fetchUserReports().then(function(data) {
			$scope.reports = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	function reportAnswer(id, answer) {
		ReportService.reportUserAnswer(id, answer).then(function(data) {
			$scope.message = data;
			fetchUserReports();
			$('#close').click();
			$scope.answer = '';
		}, function(errorResponse) {
			console.error('Error while answer Report', errorResponse);
		});
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	function infoReport(aux) {
		$('#data-slide-0').attr('class', 'active');
		$('#data-slide-1').attr('class', '');
		$('#image-index-0').attr('class', 'item active');
		$('#image-index-1').attr('class', 'item');
		$scope.report = aux;
	}

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
		return total / $scope.reportsFilter.length;
	}

	function infoAccount(aux) {
		$scope.account = aux;
	}

	function infoAccounts(aux) {
		$scope.accounts = aux;
	}

	function infoProgram(aux) {
		$scope.program = aux;
	}

	function infoReportAnswer(aux) {
		$scope.report = aux;
	}

} ]);
