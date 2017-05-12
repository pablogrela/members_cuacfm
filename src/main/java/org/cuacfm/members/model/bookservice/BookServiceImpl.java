/**
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
package org.cuacfm.members.model.bookservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.book.Book;
import org.cuacfm.members.model.book.BookDTO;
import org.cuacfm.members.model.book.BookRepository;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.UserAlreadyBookException;
import org.cuacfm.members.model.util.Constants.levels;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.Constants.typeDestinataries;
import org.cuacfm.members.model.util.Constants.typePush;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/** The Class BookServiceImpl. */
@Service("bookService")
public class BookServiceImpl implements BookService {

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private EventService eventService;

	@Autowired
	private NotificationService notificationService;

	/** Instantiates a new book service. */
	public BookServiceImpl() {
		super();
	}

	@Override
	public Book save(Book book) throws DatesException, UserAlreadyBookException {
		logger.info("save book");
		Object[] arguments = { book.getElement().getName() };

		if (book.getDateStart().before(new Date())) {
			throw new DatesException(book.getDateStart(), new Date());
		} else if (book.getDateEnd().before(book.getDateStart())) {
			throw new DatesException(book.getDateStart(), book.getDateEnd());
		}

		// Query to maybe books of element
		String message = "book.create.success";
		List<Book> books = bookRepository.getBookListConflicts(book);
		if (!books.isEmpty()) {
			String answer = messageSource.getMessage("book.create.conflict", null, Locale.getDefault());
			for (Book aux : books) {
				if (book.getAccount().getId().equals(aux.getAccount().getId())) {
					throw new UserAlreadyBookException(book.getElement().getName());
				}
				book.setAnswer(answer + " " + aux.getAccount().getFullName() + " \n");
			}
			message = "book.create.warning";
		}

		// Save Message Event
		eventService.save(message, book.getAccount(), levels.HIGH, arguments);
		return bookRepository.save(book);
	}

	@Override
	public Book update(Book book) throws DatesException {
		logger.info("update book");
		Object[] arguments = { book.getElement().getName() };

		if (book.getDateEnd().before(book.getDateStart())) {
			eventService.save("book.dateStart.error", null, levels.HIGH, arguments);
			throw new DatesException(book.getDateStart(), book.getDateEnd());
		}

		// Save Message Event
		eventService.save("book.edit.success", null, levels.MEDIUM, arguments);
		return bookRepository.update(book);

	}

	@Override
	public void delete(Book book) {
		delete(book, null);
	}

	@Override
	public void delete(Book book, Account account) {
		bookRepository.delete(book);
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public List<Book> getBookList() {
		return bookRepository.getBookList();
	}

	@Override
	public List<Book> getBookListActive() {
		return bookRepository.getBookListActive();
	}

	@Override
	public List<Book> getBookListByUser(Account account) {
		return bookRepository.getBookListByUser(account);
	}

	@Override
	public List<Book> getBookListActiveByUser(Account account) {
		return bookRepository.getBookListActiveByUser(account);
	}

	@Override
	public List<Book> getBookListClose() {
		return bookRepository.getBookListClose();
	}

	@Override
	public void up(Book book) {
		book.setActive(true);
		book.setDateRevision(null);
		book.setDateApproval(null);
		book.setState(states.MANAGEMENT);
		bookRepository.update(book);

		Object[] arguments = { book.getElement().getName() };
		eventService.save("book.up.sucess", null, levels.MEDIUM, arguments);
	}

	@Override
	public void accept(Book book) {
		book.setActive(false);
		book.setDateRevision(new Date());
		book.setDateApproval(new Date());
		book.setState(states.ACCEPT);
		bookRepository.update(book);

		Object[] arguments = { book.getElement().getName() };
		eventService.save("book.accept.success", null, levels.HIGH, arguments);
	}

	@Override
	public void deny(Book book) {
		book.setActive(false);
		book.setDateRevision(new Date());
		book.setDateApproval(null);
		book.setState(states.DENY);
		bookRepository.update(book);

		Object[] arguments = { book.getElement().getName() };
		eventService.save("book.deny.success", null, levels.HIGH, arguments);
	}

	@Override
	public Book answer(Book book, Account account, String answer, Boolean manage) {
		String log = "";
		if (book.getAnswer() != null) {
			log = book.getAnswer();
		}
		String aux = DateUtils.format(new Date(), DateUtils.FORMAT_LOCAL) + " - " + account.getName() + "\n";
		book.setAnswer(aux + "\t" + answer + "\n" + log);

		Object[] arguments = { account.getFullName(), book.getElement().getName() };
		eventService.save("book.answer.user", book.getAccount(), levels.HIGH, arguments);

		// Send notification
		if (!account.getId().equals(book.getAccount().getId())) {
			Object[] arguments2 = { book.getElement().getName() };
			String title = messageSource.getMessage("book.answer.push.title", arguments2, Locale.getDefault());
			List<Account> sendAccounts = new ArrayList<>();
			sendAccounts.add(book.getAccount());
			notificationService.sendNotification(typeDestinataries.ALL, sendAccounts, title, answer, typePush.BOOK, new Gson().toJson(getBookDTO(book)));
		}

		if (manage == null) {
			bookRepository.update(book);
		} else if (manage) {
			accept(book);
		} else {
			deny(book);
		}
		return book;
	}

	@Override
	public List<BookDTO> getBooksDTO(List<Book> books) {
		List<BookDTO> booksDTO = new ArrayList<>();
		for (Book book : books) {
			booksDTO.add(getBookDTO(book));
		}
		return booksDTO;
	}

	@Override
	public BookDTO getBookDTO(Book book) {
		BookDTO bookDTO = null;

		if (book != null) {
			bookDTO = new BookDTO(book.getId(), accountService.getAccountDTO(book.getAccount()),
					elementService.getElementDTO(book.getElement()), book.getDescription(), book.getAnswer(), book.getDateCreate(),
					book.getDateStart(), book.getDateEnd(), book.getDateRevision(), book.getDateApproval(), book.getState(),
					book.isActive());
		}
		return bookDTO;
	}

	@Override
	public Book getBook(BookDTO bookDTO, Account account) {
		if (account == null) {
			account = accountService.findByEmail(bookDTO.getAccount().getEmail());
		}

		return new Book(account, elementService.findByName(bookDTO.getElement().getName()), bookDTO.getDescription(),
				bookDTO.getDateStart(), bookDTO.getDateEnd());
	}

}
