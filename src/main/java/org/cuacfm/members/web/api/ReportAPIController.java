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
package org.cuacfm.members.web.api;

import static org.cuacfm.members.model.util.FirebaseUtils.getEmailOfToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.permissions;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.cuacfm.members.model.reportservice.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/** The Class ReportAPIController. */
@Controller
public class ReportAPIController {

	private static final Logger logger = LoggerFactory.getLogger(ReportAPIController.class);

	@Autowired
	private ReportService reportService;

	@Autowired
	private AccountService accountService;

	/**
	 * Instantiates a new training Controller.
	 */
	public ReportAPIController() {
		super();
	}

	/**
	 * Gets the reports API.
	 *
	 * @param token the token
	 * @return the reports API
	 */
	@RequestMapping(value = "api/reportList/")
	public ResponseEntity<String> getReportsAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			if (account.getPermissions().contains(permissions.ROLE_REPORT.toString())) {
				List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportListActive());
				String reportsJson = new Gson().toJson(reportsDTO);
				// Return with data "{ \"data\": " + reportsJson + " }" instead of reportsJson
				return new ResponseEntity<>(reportsJson, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Gets the reports API.
	 *
	 * @param token the token
	 * @return the reports API
	 */
	@RequestMapping(value = "api/reportUserList/")
	public ResponseEntity<String> getReportsUserAPI(@RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			List<ReportDTO> reportsDTO = reportService.getReportsDTO(reportService.getReportListActiveByUser(account));
			String reportsJson = new Gson().toJson(reportsDTO);
			// Return with data "{ \"data\": " + reportsJson + " }" instead of reportsJson
			return new ResponseEntity<>(reportsJson, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Port answer API.
	 *
	 * @param reportId the report id
	 * @param token the token
	 * @param answer the answer
	 * @return the response entity
	 */
	@RequestMapping(value = { "api/reportList/reportAnswer/{reportId}", "api/reportUserList/reportAnswer/{reportId}" }, method = RequestMethod.POST)
	public ResponseEntity<String> reportAnswerAPI(@PathVariable("reportId") Long reportId, @RequestParam(value = "token") String token,
			@RequestParam(value = "answer") String answer, @RequestParam(value = "manage") Boolean manage) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			Account account = accountService.findByEmail(email);
			Report report = reportService.findById(reportId);

			reportService.answer(report, account, answer, manage);

			ReportDTO newReportDTO = reportService.getReportDTO(report);
			String newReportJson = new Gson().toJson(newReportDTO);
			return new ResponseEntity<>(newReportJson, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Gets the image report API.
	 *
	 * @param reportId the report id
	 * @param imageName the image name
	 * @param token the token
	 * @return the image report API
	 */
	@RequestMapping(value = "api/reportList/image/{reportId}")
	@ResponseBody
	public byte[] getImageReportAPI(@PathVariable("reportId") Long reportId, @RequestParam(value = "token") String token,
			@RequestParam(value = "imageName") String imageName) {
		byte[] image = null;

		// Validate Token and retrieve email
		if (getEmailOfToken(token) != null) {
			try {
				Report report = reportService.findById(reportId);
				File serverFile = new File(report.getFile() + imageName);
				image = Files.readAllBytes(serverFile.toPath());
			} catch (Exception e) {
				logger.error("getImageReportAPI", e);
			}
		}
		return image;
	}

	/**
	 * Gets the images report API.
	 *
	 * @param reportId the report id
	 * @param token the token
	 * @return the images report API in format Gson
	 */
	@RequestMapping(value = "api/imagesReport/{reportId}")
	public ResponseEntity<String> getImagesReportAPI(@PathVariable("reportId") Long reportId, @RequestParam(value = "token") String token) {

		// Validate Token and retrieve email
		if (getEmailOfToken(token) != null) {
			Report report = reportService.findById(reportId);

			List<byte[]> files = new ArrayList<>();
			try {
				for (String imageName : report.getFiles()) {
					File serverFile = new File(report.getFile() + imageName);
					files.add(Files.readAllBytes(serverFile.toPath()));
				}
			} catch (IOException e) {
				logger.error("getImageReport", e);
			}

			return new ResponseEntity<>(new Gson().toJson(files), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}

	/**
	 * Creates the report API.
	 *
	 * @param token the token
	 * @param reportJson the report json
	 * @param photos the photos
	 * @return the response entity
	 */
	@RequestMapping(value = "api/reportList/reportCreate", method = RequestMethod.POST)
	public ResponseEntity<String> createReportAPI(@RequestParam(value = "token") String token, @RequestParam(value = "reportJson") String reportJson,
			@RequestParam(value = "photos") String photos) {

		// Validate Token and retrieve email
		String email = getEmailOfToken(token);

		if (email != null) {
			try {
				Type listType = new TypeToken<ArrayList<byte[]>>() {
				}.getType();
				List<byte[]> photosAux = new Gson().fromJson(photos, listType);

				Account account = accountService.findByEmail(email);
				ReportDTO reportDTO = new Gson().fromJson(reportJson, ReportDTO.class);

				Report report = reportService.getReport(reportDTO, account);
				report = reportService.save(report, photosAux);

				ReportDTO newReportDTO = reportService.getReportDTO(report);
				String newReportJson = new Gson().toJson(newReportDTO);
				return new ResponseEntity<>(newReportJson, HttpStatus.CREATED);
			} catch (Exception e) {
				logger.error("createReportAPI", e);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	}
}
