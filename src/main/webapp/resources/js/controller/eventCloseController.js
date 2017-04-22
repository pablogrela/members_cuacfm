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
'use strict';

angular.module('membersApp').controller('EventController', [ '$scope', 'EventService', function($scope, EventService) {
	var self = this;
	self.eventsClose;
	self.eventsOriginal;
	self.isLastMonth = true;
	$scope.enableLastMonth = true;
	self.highlight = highlight;

	fetchAllEventsClose();

	function fetchAllEventsClose() {
		EventService.fetchAllEventsClose().then(function(d) {
			self.eventsClose = d;
			self.eventsOriginal = d;
		}, function(errorResponse) {
			console.error('Error while fetching Events', errorResponse);
		});
	}

	function highlight(id) {
		EventService.highlight(id).then(fetchAllEventsClose, function(errorResponse) {
			console.error('Error while highlight Event', errorResponse);
		});
	}
	
	$scope.last_month = function() {
		if (self.isLastMonth) {
			var newEvents = [];
			var newDate = new Date();
			var lastMonth = newDate.setMonth(newDate.getMonth() - 1)
			for (var i = 0; i < self.events.length; i++) {
				var event = self.events[i];
				if (event.dateEvent > lastMonth) {
					newEvents.push(event);
				}
			}
			self.events = newEvents;
		} else {
			self.events = self.eventsOriginal;
		}
		self.isLastMonth = !self.isLastMonth;
	}

} ]);
