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
package org.cuacfm.members.web.signup;

import java.io.IOException;

import javax.script.ScriptException;
import javax.validation.Valid;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UniqueListException;
import org.cuacfm.members.model.userservice.UserService;
import org.cuacfm.members.web.signin.SigninController;
import org.cuacfm.members.web.support.MessageHelper;
import org.cuacfm.members.web.support.VerifyRecaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aeat.valida.Validador;

/** The Class SignupController. */
@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";
	private static final String SIGNUP_FIREBASE_VIEW_NAME = "signup/signupfirebase";
	private static final String SIGNUP_FIREBASE_MANUAL_VIEW_NAME = "signup/signupfirebasemanual";

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Value("${recaptcha.data.sitekey}")
	private String recaptchaDataSitekey;

	/**
	 * Instantiates a new signup Controller.
	 */
	public SignupController() {
		super();
	}

	@ModelAttribute("recaptcha")
	public String recaptcha() {
		return recaptchaDataSitekey;
	}

	/**
	 * Signup.
	 *
	 * @param email the email
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "signup")
	public String signup(@RequestParam(value = "email", required = false) String email, Model model) {
		SignupForm signupForm = new SignupForm();
		if (email != null) {
			signupForm.setEmail(email);
			signupForm.setBlockEmail(true);
			MessageHelper.addInfoAttribute(model, "signin.infoSignin", "");
		}
		model.addAttribute(signupForm);
		return SIGNUP_VIEW_NAME;
	}

	/**
	 * Signup.
	 *
	 * @param signupForm the signup form
	 * @param errors the errors
	 * @param ra the ra
	 * @param response the response
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ScriptException the script exception
	 * @throws NoSuchMethodException the no such method exception
	 */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra,
			@RequestParam("g-recaptcha-response") String response) throws IOException, ScriptException, NoSuchMethodException {

		// check that the password and rePassword are the same
		String password = signupForm.getPassword();
		String rePassword = signupForm.getRePassword();
		if (!password.equals(rePassword)) {
			errors.rejectValue("rePassword", "signup.passwordsDontMatch");
		}

		// check that rule = true
		boolean rule = signupForm.getRule();
		if (!rule) {
			errors.rejectValue("rule", "signup.existentRule", new Object[] { "rule" }, "rule");
		}

		// Los test tiene un error, ya que no pueden verificar los captcha, mejor probarlos sin internet
		if (!VerifyRecaptcha.verify(response)) {
			errors.rejectValue("captcha", "signup.captcha", new Object[] { "captcha" }, "captcha");
		}

		// Validar DNI
		Validador validador = new Validador();
		if (validador.checkNif(signupForm.getDni()) < 0) {
			errors.rejectValue("dni", "signup.dni.noValid", new Object[] { signupForm.getDni() }, "dni");
		}

		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}

		try {
			Account account = accountService.save(signupForm.createAccount());
			userService.signin(account);
		} catch (UniqueListException e) {
			for (UniqueException unique : e.getMessages()) {
				errors.rejectValue(unique.getAttribute(), "signup.existent." + unique.getAttribute(), new Object[] { unique.getValue() },
						unique.getAttribute());
			}
		}

		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}

		MessageHelper.addSuccessAttribute(ra, "signup.success");
		return SIGNUP_FIREBASE_VIEW_NAME;
	}

	/**
	 * Signup firebase manual.
	 *
	 * @param email the email
	 * @param token the token
	 * @param model the model
	 * @return the string
	 */
	// El token se genera en el migrate
	@RequestMapping(value = "signup/signupFirebaseManual")
	public String signupFirebaseManual(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "token", required = false) String token, Model model) {

		if (email == null || token == null) {
			return SigninController.SIGNIN_REDIRECT;
		}

		// If you are already registered you are redirected to signin
		Account account = accountService.findByEmail(email);
		if (account.getToken() == null || !account.getToken().equals(token)) {
			return SigninController.SIGNIN_REDIRECT;
		}

		model.addAttribute("email", email);
		model.addAttribute("token", token);
		return SIGNUP_FIREBASE_MANUAL_VIEW_NAME;
	}

	/**
	 * Signup firebase manual.
	 *
	 * @param error the error
	 * @param email the email
	 * @param token the token
	 * @param model the model
	 * @param ra the ra
	 * @return the string
	 * @throws UniqueListException the unique list exception
	 */
	@RequestMapping(value = "signup/signupFirebaseManual", method = RequestMethod.POST)
	public String signupFirebaseManual(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "username", required = false) String email, @RequestParam(value = "token", required = false) String token,
			Model model, RedirectAttributes ra) throws UniqueListException {

		if (error != null && error.contains("auth/email-already-in-use")) {
			return SigninController.SIGNIN_REDIRECT;
		}

		if (error != null || email == null || token == null) {
			MessageHelper.addErrorAttribute(ra, "firebase." + error, "password");
			return "redirect:/signup/signupFirebaseManual?email=" + email + "&token=" + token;
		}

		Account account = accountService.findByEmail(email);
		if (account == null) {
			MessageHelper.addErrorAttribute(ra, "firebase.undefined", "password");
			return "redirect:/signup/signupFirebaseManual?email=" + email + "&token=" + token;
		}

		accountService.removeToken(account);
		userService.signin(account);

		MessageHelper.addWarningAttribute(ra, "profile.reviewData", "");
		return "redirect:/profile";
	}
}
