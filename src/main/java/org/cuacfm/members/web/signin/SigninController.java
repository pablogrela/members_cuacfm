package org.cuacfm.members.web.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class SigninController. */
@Controller
public class SigninController {

	/** The Constant SIGNIN_VIEW_NAME. */
	private static final String SIGNIN_VIEW_NAME = "signin/signin";
	
	/**
	 * Instantiates a new Signin controller.
	 */
	public SigninController() {
		// Default empty constructor.
	}

	/**
	 * Signin.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "signin")
	public String signin() {
		return SIGNIN_VIEW_NAME;
	}
}
