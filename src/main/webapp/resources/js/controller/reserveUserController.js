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
membersApp.controller('ReserveUserController', [ '$scope', 'ReserveService', function($scope, ReserveService) {
	$scope.sortType;
	$scope.search;
	$scope.isLastMonth = true;
	$scope.enableLastMonth = true;
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.reserve;
	$scope.reserves;
	$scope.reservesOriginal;
	$scope.element;
	$scope.message;

	$scope.fetchUserReserves = function() {
		ReserveService.fetchUserReserves().then(function(data) {
			$scope.reserves = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.reserveUp = function(id) {
		ReserveService.reserveUp(id).then(function(data) {
			$scope.message = data;
			$scope.fetchUserReserves();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Up Reserve', errorResponse);
		});
	}

	$scope.reserveAccept = function(id) {
		ReserveService.reserveAccept(id).then(function(data) {
			$scope.message = data;
			$scope.fetchUserReserves();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Accept Reserve', errorResponse);
		});
	}

	$scope.reserveDeny = function(id) {
		ReserveService.reserveDeny(id).then(function(data) {
			$scope.message = data;
			$scope.fetchUserReserves();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Deny Reserve', errorResponse);
		});
	}

	$scope.reserveAnswer = function(id, answer) {
		ReserveService.reserveUserAnswer(id, answer).then(function(data) {
			$scope.message = data;
			$scope.fetchUserReserves();
			$('#close').click();
			$scope.answer = '';
		}, function(errorResponse) {
			console.error('Error while answer Reserve', errorResponse);
		});
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	$scope.lastMonth = function() {
		if (self.isLastMonth) {
			var newReserve = [];
			var newDate = new Date();
			var month = newDate.setMonth(newDate.getMonth() - 1)
			for (var i = 0; i < self.reserves.length; i++) {
				var event = self.reserve[i];
				if (event.dateEvent > month) {
					newReserve.push(event);
				}
			}
			self.reserves = newReserve;
		} else {
			self.reserves = self.reserveOriginal;
		}
		self.isLastMonth = !self.isLastMonth;
	}

	$scope.infoReserve = function(aux) {
		$scope.reserve = aux;
	}

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.infoElement = function(aux) {
		$scope.element = aux;
	}

	$scope.infoReserveAnswer = function(aux) {
		$scope.reserve = aux;
	}

} ]);
