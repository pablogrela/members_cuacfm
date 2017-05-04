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
package org.cuacfm.members.model.reserveservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.UserAlreadyReserveException;
import org.cuacfm.members.model.reserve.Reserve;
import org.cuacfm.members.model.reserve.ReserveDTO;
import org.cuacfm.members.model.reserve.ReserveRepository;
import org.cuacfm.members.model.util.Constants.levels;
import org.cuacfm.members.model.util.Constants.states;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/** The Class ReserveServiceImpl. */
@Service("reserveService")
public class ReserveServiceImpl implements ReserveService {

	private static final Logger logger = LoggerFactory.getLogger(ReserveServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;

	@Autowired
	private ReserveRepository reserveRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new reserve service. */
	public ReserveServiceImpl() {
		super();
	}

	@Override
	public Reserve save(Reserve reserve) throws DatesException, UserAlreadyReserveException {
		logger.info("save reserve");
		Object[] arguments = { reserve.getElement().getName() };

		if (reserve.getDateStart().before(new Date())) {
			throw new DatesException(reserve.getDateStart(), new Date());
		} else if (reserve.getDateEnd().before(reserve.getDateStart())) {
			throw new DatesException(reserve.getDateStart(), reserve.getDateEnd());
		}

		// Query to maybe reserves of element
		String message = "reserve.create.success";
		List<Reserve> reserves = reserveRepository.getReserveListConflicts(reserve);
		if (!reserves.isEmpty()) {
			String answer = messageSource.getMessage("reserve.create.conflict", null, Locale.getDefault());
			for (Reserve aux : reserves) {
				if (reserve.getAccount().getId().equals(aux.getAccount().getId())) {
					throw new UserAlreadyReserveException(reserve.getElement().getName());
				}
				reserve.setAnswer(answer + " " + aux.getAccount().getFullName() + " \n");
			}
			message = "reserve.create.warning";
		}

		// Save Message Event
		eventService.save(message, reserve.getAccount(), levels.HIGH, arguments);
		return reserveRepository.save(reserve);
	}

	@Override
	public Reserve update(Reserve reserve) throws DatesException {
		logger.info("update reserve");
		Object[] arguments = { reserve.getElement().getName() };

		if (reserve.getDateEnd().before(reserve.getDateStart())) {
			eventService.save("reserve.dateStart.error", null, levels.HIGH, arguments);
			throw new DatesException(reserve.getDateStart(), reserve.getDateEnd());
		}

		// Save Message Event
		eventService.save("reserve.edit.success", null, levels.MEDIUM, arguments);
		return reserveRepository.update(reserve);

	}

	@Override
	public void delete(Reserve reserve) {
		delete(reserve, null);
	}

	@Override
	public void delete(Reserve reserve, Account account) {
		reserveRepository.delete(reserve);
	}

	@Override
	public Reserve findById(Long id) {
		return reserveRepository.findById(id);
	}

	@Override
	public List<Reserve> getReserveList() {
		return reserveRepository.getReserveList();
	}

	@Override
	public List<Reserve> getReserveListActive() {
		return reserveRepository.getReserveListActive();
	}

	@Override
	public List<Reserve> getReserveListByUser(Account account) {
		return reserveRepository.getReserveListByUser(account);
	}

	@Override
	public List<Reserve> getReserveListActiveByUser(Account account) {
		return reserveRepository.getReserveListActiveByUser(account);
	}

	@Override
	public List<Reserve> getReserveListClose() {
		return reserveRepository.getReserveListClose();
	}

	@Override
	public void up(Reserve reserve) {
		reserve.setActive(true);
		reserve.setDateRevision(null);
		reserve.setDateApproval(null);
		reserve.setState(states.MANAGEMENT);
		reserveRepository.update(reserve);

		Object[] arguments = { reserve.getElement().getName() };
		eventService.save("reserve.up.sucess", null, levels.MEDIUM, arguments);
	}

	@Override
	public void accept(Reserve reserve) {
		reserve.setActive(false);
		reserve.setDateRevision(new Date());
		reserve.setDateApproval(new Date());
		reserve.setState(states.ACCEPT);
		reserveRepository.update(reserve);

		Object[] arguments = { reserve.getElement().getName() };
		eventService.save("reserve.accept.success", null, levels.HIGH, arguments);
	}

	@Override
	public void deny(Reserve reserve) {
		reserve.setActive(false);
		reserve.setDateRevision(new Date());
		reserve.setDateApproval(null);
		reserve.setState(states.DENY);
		reserveRepository.update(reserve);

		Object[] arguments = { reserve.getElement().getName() };
		eventService.save("reserve.deny.success", null, levels.HIGH, arguments);
	}

	@Override
	public Reserve answer(Reserve reserve, Account account, String answer, Boolean manage) {
		String log = "";
		if (reserve.getAnswer() != null) {
			log = reserve.getAnswer();
		}
		String aux = DateUtils.format(new Date(), DateUtils.FORMAT_DISPLAY) + " - " + account.getName() + "\n";
		reserve.setAnswer(aux + "\t" + answer + "\n" + log);

		Object[] arguments = { account.getFullName(), reserve.getElement().getName() };
		eventService.save("reserve.answer.user", reserve.getAccount(), levels.HIGH, arguments);

		// Send push
		if (!account.getId().equals(reserve.getAccount().getId())) {
			Object[] arguments2 = { reserve.getElement().getName() };
			String title = messageSource.getMessage("reserve.answer.push.title", arguments2, Locale.getDefault());
			PushService.sendPushNotificationToDevice(reserve.getAccount().getDevicesToken(), title, answer);
		}

		if (manage == null) {
			reserveRepository.update(reserve);
		} else if (manage) {
			accept(reserve);
		} else {
			deny(reserve);
		}
		return reserve;
	}

	@Override
	public List<ReserveDTO> getReservesDTO(List<Reserve> reserves) {
		List<ReserveDTO> reservesDTO = new ArrayList<>();
		for (Reserve reserve : reserves) {
			reservesDTO.add(getReserveDTO(reserve));
		}
		return reservesDTO;
	}

	@Override
	public ReserveDTO getReserveDTO(Reserve reserve) {
		ReserveDTO reserveDTO = null;

		if (reserve != null) {
			reserveDTO = new ReserveDTO(reserve.getId(), accountService.getAccountDTO(reserve.getAccount()),
					elementService.getElementDTO(reserve.getElement()), reserve.getDescription(), reserve.getAnswer(), reserve.getDateCreate(),
					reserve.getDateStart(), reserve.getDateEnd(), reserve.getDateRevision(), reserve.getDateApproval(), reserve.getState(),
					reserve.isActive());
		}
		return reserveDTO;
	}

	@Override
	public Reserve getReserve(ReserveDTO reserveDTO, Account account) {
		if (account == null) {
			account = accountService.findByEmail(reserveDTO.getAccount().getEmail());
		}

		return new Reserve(account, elementService.findByName(reserveDTO.getElement().getName()), reserveDTO.getDescription(),
				reserveDTO.getDateStart(), reserveDTO.getDateEnd());
	}

}
