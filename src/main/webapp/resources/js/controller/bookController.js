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
membersApp.controller('BookController', [ '$scope', 'BookService', function($scope, BookService) {
	$scope.sortType;
	$scope.search;
	$scope.booksOriginal;
	$scope.isLastMonth = true;
	$scope.enableLastMonth = true;
	$scope.endDate = new Date();
	$scope.sortReverse = false;
	$scope.numPerPage = 20;
	$scope.account;
	$scope.book;
	$scope.books;
	$scope.element;
	$scope.message;

	$scope.fetchAllBooks = function() {
		BookService.fetchAllBooks().then(function(data) {
			$scope.books = data;
			$scope.booksOriginal = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.fetchAllBooksClose = function() {
		BookService.fetchAllBooksClose().then(function(data) {
			$scope.books = data;
			$scope.booksOriginal = data;
		}, function(errorResponse) {
			console.error('Error while fetching Users', errorResponse);
		});
	}

	$scope.bookUp = function(id) {
		BookService.bookUp(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllBooksClose();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Up Book', errorResponse);
		});
	}

	$scope.bookAccept = function(id) {
		BookService.bookAccept(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllBooks();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Accept Book', errorResponse);
		});
	}

	$scope.bookDeny = function(id) {
		BookService.bookDeny(id).then(function(data) {
			$scope.message = data;
			$scope.fetchAllBooks();
			showModal(modal);
		}, function(errorResponse) {
			console.error('Error while Deny Book', errorResponse);
		});
	}

	$scope.bookAnswer = function(id, answer) {
		if (answer != null && !jQuery.isEmptyObject(answer)) {
			BookService.bookAnswer(id, answer).then(function(data) {
				$scope.message = data;
				$scope.fetchAllBooks();
				$('#close').click();
				$scope.answer = '';
			}, function(errorResponse) {
				console.error('Error while answer Book', errorResponse);
			});
		}
	}

	$scope.searchDates = function() {
		if ($scope.endDate != null && $scope.startDate != null) {
			$scope.books = [];
			for (var i = 0; i < $scope.booksOriginal.length; i++) {
				var book = $scope.booksOriginal[i];
				if (book.dateCreate > $scope.startDate && book.dateCreate <= $scope.endDate) {
					$scope.books.push(book);
				} else {
					var index = $scope.books.indexOf(event);
					$scope.books.splice(index, 1);
				}
			}
		}
	}

	$scope.localeSensitiveComparator = function(v1, v2) {
		if (v1.type == 'string' || v2.type == 'string') {
			return v1.value.localeCompare(v2.value);
		}
		return (v1.value < v2.value) ? -1 : 1;
	};

	$scope.lastMonth = function() {
		if (self.isLastMonth) {
			var newBook = [];
			var newDate = new Date();
			var month = newDate.setMonth(newDate.getMonth() - 1)
			for (var i = 0; i < self.books.length; i++) {
				var event = self.book[i];
				if (event.dateEvent > month) {
					newBook.push(event);
				}
			}
			self.books = newBook;
		} else {
			self.books = self.bookOriginal;
		}
		self.isLastMonth = !self.isLastMonth;
	}

	$scope.infoBook = function(aux) {
		$scope.book = aux;
	}

	$scope.infoAccount = function(aux) {
		$scope.account = aux;
	}

	$scope.infoElement = function(aux) {
		$scope.element = aux;
	}

	$scope.infoBookAnswer = function(aux) {
		$scope.book = aux;
	}

} ]);
