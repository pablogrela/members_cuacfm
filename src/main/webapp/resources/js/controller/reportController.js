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
	$scope.program = '';
	$scope.message = '';

	var self = this;
	self.reportDown = reportDown;
	self.infoAccount = infoAccount;
	self.infoAccounts = infoAccounts;
	self.infoProgram = infoProgram;
	self.infoReport = infoReport;
	self.reportAnswer = reportAnswer;
	self.infoReportAnswer = infoReportAnswer;

	fetchAllReports();

	function fetchAllReports() {
		ReportService.fetchAllReports().then(function(data) {
			$scope.reports = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	function reportDown(id) {
		ReportService.reportDown(id).then(function(data) {
			$scope.message = data;
			fetchAllReports();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Down Report', errorResponse);
		});
	}

	function reportAnswer(id, answer) {
		ReportService.reportAnswer(id, answer).then(function(data) {
			$scope.message = data;
			fetchAllReports();
			//showModal(modal);
			// Close modal report
			$('#close').click();
		}, function(errorResponse) {
			console.error('Error while answer Report', errorResponse);
		});
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		// If we don't get strings, just compare by index
		if (v1.type !== 'string' || v2.type !== 'string') {
			return (v1.index < v2.index) ? -1 : 1;
		}

		// Compare strings alphabetically, taking locale into account
		return v1.value.localeCompare(v2.value);
	};

	function infoReport(aux) {
		// Reset carousel to first image
		// $('#carousel-reports').carousel(0);
		$('#data-slide-0').attr('class', 'active');
		$('#data-slide-1').attr('class', '');
		$('#image-index-0').attr('class', 'item active');
		$('#image-index-1').attr('class', 'item');
		$scope.report = aux;
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