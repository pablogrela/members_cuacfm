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
package org.cuacfm.members.web.reserve;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.element.Element;
import org.cuacfm.members.model.elementservice.ElementService;
import org.cuacfm.members.model.exceptions.DatesException;
import org.cuacfm.members.model.exceptions.UserAlreadyReserveException;
import org.cuacfm.members.model.reserve.Reserve;
import org.cuacfm.members.model.reserveservice.ReserveService;
import org.cuacfm.members.web.support.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class ReserveCreateController. */
@Controller
public class ReserveCreateController {

	private static final Logger logger = LoggerFactory.getLogger(ReserveCreateController.class);
	private static final String RESERVE_VIEW_NAME = "reserve/reservecreate";

	@Autowired
	private ReserveService reserveService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ElementService elementService;

	private ReserveForm reserveForm;

	/**
	 * Instantiates a new reserveController.
	 */
	public ReserveCreateController() {
		super();
	}

	/**
	 * Reserve form.
	 *
	 * @return the reserve form
	 */
	@ModelAttribute("reserveForm")
	public ReserveForm reserveForm() {
		return reserveForm;
	}

	/**
	 * Reserve.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "reserveList/reserveCreate")
	public String reserve(Model model) {
		reserveForm = new ReserveForm();
		List<Element> elements = elementService.getElementListReservable();
		reserveForm.setElements(elements);
		model.addAttribute(reserveForm);
		model.addAttribute("reserveCreate", "reserveCreate");
		model.addAttribute("reserve", "reserves");
		return RESERVE_VIEW_NAME;
	}

	/**
	 * Reserve user.
	 *
	 * @param model the model
	 * @param principal the principal
	 * @return the string
	 */
	@RequestMapping(value = "reserveUserList/reserveUserCreate")
	public String reserveUser(Model model, Principal principal) {
		reserveForm = new ReserveForm();

		List<Element> elements = elementService.getElementListReservable();
		reserveForm.setElements(elements);
		model.addAttribute(reserveForm);
		model.addAttribute("reserveCreate", "reserveUserCreate");
		model.addAttribute("reserve", "reserve.list.user");
		return RESERVE_VIEW_NAME;
	}

	/**
	 * Creates the reserve authorize.
	 *
	 * @param reserveForm the reserve form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "reserveList/reserveCreate", method = RequestMethod.POST, params = { "create" })
	public String createReserveAuthorize(@Valid @ModelAttribute ReserveForm reserveForm, Principal principal, Errors errors, RedirectAttributes ra) {
		return createReserve(reserveForm, principal, errors, ra, "redirect:/reserveList");
	}

	/**
	 * Creates the reserve user.
	 *
	 * @param reserveForm the reserve form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "reserveUserList/reserveUserCreate", method = RequestMethod.POST, params = { "create" })
	public String createReserveUser(@Valid @ModelAttribute ReserveForm reserveForm, Principal principal, Errors errors, RedirectAttributes ra) {
		return createReserve(reserveForm, principal, errors, ra, "redirect:/reserveUserList");
	}

	/**
	 * Creates the reserve.
	 *
	 * @param reserveForm the reserve form
	 * @param principal the principal
	 * @param errors the errors
	 * @param ra the ra
	 * @param redirect the redirect
	 * @return the string
	 */
	private String createReserve(ReserveForm reserveForm, Principal principal, Errors errors, RedirectAttributes ra, String redirect) {

		if (errors.hasErrors()) {
			return RESERVE_VIEW_NAME;
		}

		Reserve reserve;
		try {
			Account account = accountService.findByLogin(principal.getName());
			reserve = reserveService.save(reserveForm.createReserve(account));
		} catch (DatesException e) {
			logger.error("createReserve", e);
			if (e.getDateLimit().before(new Date())) {
				errors.rejectValue("dateStart", "reserve.dateStart.past", new Object[] { e.getDateTraining() }, "dateStart");
			} else {
				errors.rejectValue("dateEnd", "reserve.dateStart.error", new Object[] { e.getDateTraining() }, "dateEnd");
			}
			return RESERVE_VIEW_NAME;
		} catch (UserAlreadyReserveException e) {
			logger.error("createReserve", e);
			errors.rejectValue("name", "reserve.create.error.already", new Object[] { e.getName() }, "name");
			return RESERVE_VIEW_NAME;
		} catch (Exception e) {
			logger.error("createReserve", e);
			errors.rejectValue("element", "reserve.create.error", new Object[] { e }, "element");
			return RESERVE_VIEW_NAME;
		}

		if (reserve.getAnswer() != null && !reserve.getAnswer().isEmpty()) {
			MessageHelper.addWarningAttribute(ra, "reserve.create.warning", reserveForm.getElement().getName());
		} else {
			MessageHelper.addSuccessAttribute(ra, "reserve.create.success", reserveForm.getElement().getName());
		}
		return redirect;
	}

}
