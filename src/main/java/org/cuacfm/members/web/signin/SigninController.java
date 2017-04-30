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
package org.cuacfm.members.web.signin;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.io.IOException;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.userservice.UserService;
import org.cuacfm.members.web.home.HomeController;
import org.cuacfm.members.web.support.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** The Class SigninController. */
@Controller
public class SigninController {

	//private static final Logger logger = LoggerFactory.getLogger(SigninController.class)

	public static final String SIGNIN_VIEW_NAME = "signin/signin";
	public static final String SIGNIN_REDIRECT = "redirect:/signin";
	public static final String RESTORE_PASSWORD_VIEW_NAME = "signin/restorepassword";
	public static final String RESET_PASSWORD_VIEW_NAME = "signin/resetpassword";
	public static final String BADCREDENTIALS = "signin.errorBadCredentials";

	@Value("${pathConfig}")
	private String pathConfig;

	@Value("${configFirebase}")
	private String configFirebase;

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new Signin controller.
	 */
	public SigninController() {
		super();
	}

	/**
	 * Signin.
	 *
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "signin")
	public String signin() throws IOException {
		return SIGNIN_VIEW_NAME;
	}

	/**
	 * Authenticate.
	 *
	 * @param token the token
	 * @param error the error
	 * @param email the email
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "signin", method = RequestMethod.POST)
	public String signin(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "error", required = false) String error,
			@RequestParam("username") String email, RedirectAttributes ra) {

		String message;
		if (error != null) {
			message = "firebase." + error;
		} else {

			// Check if the user exists, si no existe en los miembros se redirige un signup
			Account account = accountService.findByEmail(email);
			if (account == null) {
				return "redirect:/signup?email=" + email;
			}

			// Validate Token
			String emailVerified = getEmailOfToken(token);
			if (emailVerified != null && emailVerified.equals(email)) {

				// TODO prueba de cambio de idioma					
				// response.addCookie(new Cookie("lang", "gl"));
				// response.setHeader("Content-Language", "gl");

				// Locale.setDefault(new Locale("gl","ES"));
				// System.setProperty("user.language", "gl");
				// System.setProperty("user.country", "ES");		
				// Locale.getDefault();

				// Token remember-me, se podria re-implementar
				// O hacer la redireccion a /authenticate
				// TokenBasedRememberMeServices a = new TokenBasedRememberMeServices("remember-me-key", userService);
				// return "redirect:/authenticate?username="+email+"?password="+"123456";

				userService.signin(account);
				return HomeController.REDIRECT_HOME;
			}
			message = BADCREDENTIALS;
		}

		MessageHelper.addErrorAttribute(ra, message, "");
		return SIGNIN_REDIRECT;
	}

	/**
	 * Reset Password.
	 *
	 * @param mode the mode
	 * @param oobCode the oob code
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "signin/resetPassword")
	public String resetPassword(@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "oobCode", required = false) String oobCode, Model model) {
		model.addAttribute("oobCode", oobCode);
		return RESET_PASSWORD_VIEW_NAME;
	}

	/**
	 * Reset password.
	 *
	 * @param token the token
	 * @param error the error
	 * @param email the email
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "signin/resetPassword", method = RequestMethod.POST)
	public String resetPassword(@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "error", required = false) String error, @RequestParam(value = "username", required = false) String email,
			RedirectAttributes ra) {

		if (error != null) {
			String message = "firebase." + error;
			MessageHelper.addErrorAttribute(ra, message, "password");
			return "redirect:/signin/resetPassword";
		}

		// If everything is correct, the signin is made
		return signin(token, error, email, ra);
	}

	/**
	 * Restore password.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "signin/restorePassword")
	public String restorePassword() {
		return RESTORE_PASSWORD_VIEW_NAME;
	}

	/**
	 * Restore password.
	 *
	 * @param error the error
	 * @param email the email
	 * @param ra the ra
	 * @return the string
	 */
	@RequestMapping(value = "signin/restorePassword", method = RequestMethod.POST)
	public String sendRestorePassword(@RequestParam(value = "error", required = false) String error, @RequestParam("username") String email,
			RedirectAttributes ra) {

		if (error == null) {
			MessageHelper.addInfoAttribute(ra, "signin.successRestorePassword", email);
			return SIGNIN_REDIRECT;
		}

		MessageHelper.addErrorAttribute(ra, "firebase." + error, "");
		return "redirect:/restorePassword";
	}
}
