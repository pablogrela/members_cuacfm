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
package org.cuacfm.members.web.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;

/** The Class CustomErrorController. */
@Controller
class CustomErrorController {

	private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

	/** Instantiates a new custom error controller. */
	public CustomErrorController() {
		super();
	}

	/**
	 * Display an error page, as defined in web.xml <code>custom-error</code> element.
	 *
	 * @param request the request
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("generalError")
	public String generalError(HttpServletRequest request, Model model) {
		// retrieve some useful information from the request
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		// String servletName = (String)
		// request.getAttribute("javax.servlet.error.servlet_name")
		String exceptionMessage = getExceptionMessage(throwable, statusCode);

		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		String message = MessageFormat.format("{0} returned for {1} with message {2}", statusCode, requestUri, exceptionMessage);
		model.addAttribute("errorMessage", message);

		// If you do not want to display the error on the screen, put return null
		return "error/general";
	}

	/**
	 * Gets the exception message.
	 *
	 * @param throwable the throwable
	 * @param statusCode the status code
	 * @return the exception message
	 */
	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		if (throwable != null) {
			logger.error("generalError: ", throwable);
			return Throwables.getRootCause(throwable).getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
