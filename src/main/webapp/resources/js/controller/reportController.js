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
membersApp.controller('ReportController', [ '$scope', 'ReportService', function($scope, ReportService) {
	$scope.sortType;
	$scope.search;
	$scope.isLastMonth = true;
	$scope.enableLastMonth = true;
	$scope.endDate = new Date();
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.report;
	$scope.reports;
	$scope.reportsFilter;
	$scope.reportsOriginal;
	$scope.program;
	$scope.message;

	$scope.fetchAllReports = function() {
		ReportService.fetchAllReports().then(function(data) {
			$scope.reports = data;
			$scope.reportsOriginal = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.fetchAllReportsClose = function() {
		ReportService.fetchAllReportsClose().then(function(data) {
			$scope.reports = data;
			$scope.reportsOriginal = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.reportUp = function(id) {
		ReportService.reportUp(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllReportsClose();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Up Report', errorResponse);
		});
	}

	$scope.reportDown = function(id) {
		ReportService.reportDown(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllReports();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Down Report', errorResponse);
		});
	}

	$scope.reportAnswer = function(id, answer) {
		if (answer != null && !jQuery.isEmptyObject(answer)) {
			ReportService.reportAnswer(id, answer).then(function(data) {
				$scope.message = data;
				$scope.fetchAllReports();
				// showModal(modal);
				// Close modal report
				$('#close').click();
				$scope.answer = '';
			}, function(errorResponse) {
				console.error('Error while answer Report', errorResponse);
			});
		}
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	$scope.searchDates = function() {
		if ($scope.endDate != null && $scope.startDate != null) {
			$scope.reports = [];
			for (var i = 0; i < $scope.reportsOriginal.length; i++) {
				var report = $scope.reportsOriginal[i];
				if (report.dateCreate > $scope.startDate && report.dateCreate <= $scope.endDate) {
					$scope.reports.push(report);
				} else {
					var index = $scope.reports.indexOf(event);
					$scope.reports.splice(index, 1);
				}
			}
		}
	}
	
	$scope.lastMonth = function() {
		if ($scope.isLastMonth) {
			var newReport = [];
			var newDate = new Date();
			var month = newDate.setMonth(newDate.getMonth() - 1)
			for (var i = 0; i < self.reportsFilter.length; i++) {
				var event = self.report[i];
				if (event.dateEvent > month) {
					newReport.push(event);
				}
			}
			$scope.reports = newReport;
		} else {
			$scope.reports = $scope.reportOriginal;
		}
		$scope.isLastMonth = !$scope.isLastMonth;
	}

	$scope.dirtAverage = function() {
		if ($scope.reportsFilter != null) {
			var total = 0;
			for (var i = 0; i < $scope.reportsFilter.length; i++) {
				var report = $scope.reportsFilter[i];
				total += report.dirt;
			}
			return total / $scope.reportsFilter.length;
		}
	}

	$scope.tidyAverage = function() {
		if ($scope.reportsFilter != null) {
			var total = 0;
			for (var i = 0; i < $scope.reportsFilter.length; i++) {
				var report = $scope.reportsFilter[i];
				total += report.tidy;
			}
			return total / $scope.reportsFilter.length;
		}
	}

	$scope.configurationAverage = function() {
		if ($scope.reportsFilter != null) {
			var total = 0;
			for (var i = 0; i < $scope.reportsFilter.length; i++) {
				var report = $scope.reportsFilter[i];
				total += report.configuration;
			}
			return total / $scope.reportsFilter.length;
		}
	}

	$scope.infoReport = function(aux) {
		// Reset carousel to first image
		// $('#carousel-reports').carousel(0);
		$('#data-slide-0').attr('class', 'active');
		$('.indicator.active').attr('class', 'indicator');
		$('.item.active').attr('class', 'item');
		$('#image-index-0').attr('class', 'item active');
		$scope.report = aux;
	}

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.infoProgram = function(aux) {
		$scope.program = aux;
	}

	$scope.infoReportAnswer = function(aux) {
		$scope.report = aux;
	}

} ]);
