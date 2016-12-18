/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

'use strict';

membersApp.controller('EventController', [ '$scope', '$rootScope', 'EventService', function($scope, $rootScope, EventService) {
	var self = this;
	self.events;
	self.highlight = highlight;
	self.remove = remove;

	fetchAllEvents();

	function fetchAllEvents() {
		EventService.fetchAllEvents().then(function(d) {
			self.events = d;
		}, function(errResponse) {
			console.error('Error while fetching Events');
		});
	}

	function highlight(id) {
		EventService.highlight(id).then(fetchAllEvents, function(errResponse) {
			console.error('Error while highlight Event');
		});
	}

	function remove(id) {
		EventService.remove(id).then(fetchAllEvents, function(errResponse) {
			console.error('Error while highlight Event');
		});
	}

	$rootScope.$on("fetchAllEvents", function() {
		fetchAllEvents();
	});

} ]);