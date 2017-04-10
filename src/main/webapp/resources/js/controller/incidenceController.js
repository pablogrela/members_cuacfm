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
membersApp.controller('IncidenceController', [ '$scope', 'IncidenceService', function($scope, IncidenceService) {
	$scope.sortType = '';
	$scope.search = '';
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account = '';
	$scope.accounts = '';
	$scope.incidence = '';
	$scope.incidences = '';
	$scope.program = '';
	$scope.message = '';

	var self = this;
	self.incidenceDown = incidenceDown;
	self.infoAccount = infoAccount;
	self.infoAccounts = infoAccounts;
	self.infoProgram = infoProgram;
	self.infoIncidence = infoIncidence;
	self.incidenceAnswer = incidenceAnswer;
	self.infoIncidenceAnswer = infoIncidenceAnswer;

	fetchAllIncidences();

	function fetchAllIncidences() {
		IncidenceService.fetchAllIncidences().then(function(data) {
			$scope.incidences = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	function incidenceDown(id) {
		IncidenceService.incidenceDown(id).then(function(data) {
			$scope.message = data;
			fetchAllIncidences();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Down Incidence', errorResponse);
		});
	}

	function incidenceAnswer(id, answer) {
		IncidenceService.incidenceAnswer(id, answer).then(function(data) {
			$scope.message = data;
			fetchAllIncidences();
			showModal(modal);
			// Close modal incidence
			$('#close').click();
		}, function(errorResponse) {
			console.error('Error while answer Incidence', errorResponse);
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

	function infoIncidence(aux) {
		// Reset carousel to first image
		// $('#carousel-incidences').carousel(0);
		$('#data-slide-0').attr('class', 'active');
		$('#data-slide-1').attr('class', '');
		$('#image-index-0').attr('class', 'item active');
		$('#image-index-1').attr('class', 'item');
		$scope.incidence = aux;
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

	function infoIncidenceAnswer(aux) {
		$scope.incidence = aux;
	}

} ]);
