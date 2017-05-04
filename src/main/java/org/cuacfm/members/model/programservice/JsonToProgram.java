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
package org.cuacfm.members.model.programservice;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.program.ProgramCategory;
import org.cuacfm.members.model.program.ProgramLanguage;
import org.cuacfm.members.model.program.ProgramThematic;
import org.cuacfm.members.model.program.ProgramType;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class JsonToProgram.
 */
@Service("jsonToProgram")
public class JsonToProgram {

	private static final Logger logger = LoggerFactory.getLogger(JsonToProgram.class);

	private static final int MAX_AREA = 500;
	private static final int MAX_CHARACTERS = 50;
	private static final int MAX_DNI = 10;
	private static final int MAX_NUMBER = 20;

	@Value("${formPrograms}")
	private String formPrograms;

	@Autowired
	private ProgramService programService;

	@Autowired
	private AccountService accountService;

	/**
	 * Parser.
	 *
	 * @param pathJsonToAccount the path json to account
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public void parser(String pathJsonToAccount) throws Exception {
		logger.info("parser");

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new InputStreamReader(new FileInputStream(pathJsonToAccount), StandardCharsets.UTF_8));
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray solutions = (JSONArray) jsonObject.get(formPrograms);

		Iterator<JSONObject> iterator = solutions.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObjectRow = iterator.next();

			String dateString = FileUtils.split((String) jsonObjectRow.get("dateCreate"), MAX_CHARACTERS);
			String[] dateConvert = dateString.split("GMT");
			Date dateCreate = DateUtils.format(dateConvert[0].trim(), "E MMM dd yyyy HH:mm:ss", Locale.ENGLISH);

			String name = FileUtils.split((String) jsonObjectRow.get("name"), MAX_CHARACTERS);
			String description = FileUtils.split((String) jsonObjectRow.get("description"), MAX_AREA);
			description = description + "\n" + FileUtils.split((String) jsonObjectRow.get("hour"), MAX_AREA);
			Float periodicity = FileUtils.getFloat((String) jsonObjectRow.get("periodicity"));
			Integer duration = FileUtils.getInteger((String) jsonObjectRow.get("duration"));

			// Conseguir los miembros, mejor sacar la relacion de programName, dentro de accounts.json
			// String members = FileUtils.split((String) jsonObjectRow.get("accounts"), MAX_CHARACTERS);
			List<Account> accounts = getMembers(name);

			String programLanguageName = FileUtils.split((String) jsonObjectRow.get("programLanguage"), MAX_DNI);
			ProgramLanguage programLanguage = programService.findProgramLanguageByName(programLanguageName);

			String programTypeName = FileUtils.split((String) jsonObjectRow.get("programType"), MAX_NUMBER);
			ProgramType programType = programService.findProgramTypeByName(programTypeName);

			// Hay mas de un programThematic en el json, pero solo cojo el primero
			String programThematicName = FileUtils.split((String) jsonObjectRow.get("programThematic"), MAX_NUMBER);
			String[] programThematicName1 = programThematicName.split(",");
			ProgramThematic programThematic = programService.findProgramThematicByName(programThematicName1[0].trim());

			String programCategoryName = FileUtils.split((String) jsonObjectRow.get("programCategory"), MAX_NUMBER);
			ProgramCategory programCategory = programService.findProgramCategoryByName(programCategoryName);

			String email = FileUtils.split((String) jsonObjectRow.get("email"), MAX_CHARACTERS).trim();
			String twitter = FileUtils.split((String) jsonObjectRow.get("twitter"), MAX_CHARACTERS).trim();
			String facebook = FileUtils.split((String) jsonObjectRow.get("facebook"), MAX_CHARACTERS).trim();
			String podcast = FileUtils.split((String) jsonObjectRow.get("podcast"), MAX_CHARACTERS).trim();
			String web = FileUtils.split((String) jsonObjectRow.get("web"), MAX_CHARACTERS).trim();

			Boolean active = FileUtils.getBoolean((String) jsonObjectRow.get("active"));
			String accountPayerName = FileUtils.split((String) jsonObjectRow.get("accountPayer"), MAX_CHARACTERS);
			Account accountPayer = accountService.findByEmail(accountPayerName);

			try {
				Program programAux = programService.findByName(name);

				//UPDATE
				if (programAux != null) {
					programAux.setName(FileUtils.changeValue(programAux.getName(), name));
					programAux.setDescription(FileUtils.changeValue(programAux.getDescription(), description));
					programAux.setPeriodicity((Float) FileUtils.changeValue(periodicity, (float) 0.0));
					programAux.setDuration((Integer) FileUtils.changeValue(duration, 60));
					programAux.setAccounts(accounts);
					programAux.setProgramType((ProgramType) FileUtils.changeValue(programAux.getProgramType(), programType));
					programAux.setProgramThematic((ProgramThematic) FileUtils.changeValue(programAux.getProgramThematic(), programThematic));
					programAux.setProgramCategory((ProgramCategory) FileUtils.changeValue(programAux.getProgramCategory(), programCategory));
					programAux.setProgramLanguage((ProgramLanguage) FileUtils.changeValue(programAux.getProgramLanguage(), programLanguage));
					programAux.setEmail(FileUtils.changeValue(programAux.getEmail(), email));
					programAux.setTwitter(FileUtils.changeValue(programAux.getTwitter(), twitter));
					programAux.setFacebook(FileUtils.changeValue(programAux.getFacebook(), facebook));
					programAux.setPodcast(FileUtils.changeValue(programAux.getPodcast(), podcast));
					programAux.setWeb(FileUtils.changeValue(programAux.getWeb(), web));
					programAux.setDateCreate((Date) FileUtils.changeValue(programAux.getDateCreate(), dateCreate));
					programAux.setActive((Boolean) FileUtils.changeValue(programAux.isActive(), active));
					programAux.setAccountPayer(accountPayer);
					programService.update(programAux);

				} else {
					// Default values, if property is null
					periodicity = (Float) FileUtils.changeValue((float) 1.0, periodicity);
					duration = (Integer) FileUtils.changeValue(60, duration);
					programCategory = (ProgramCategory) FileUtils.changeValue(programCategory, programService.findProgramCategoryById(1));
					programThematic = (ProgramThematic) FileUtils.changeValue(programThematic, programService.findProgramThematicById(1));
					programLanguage = (ProgramLanguage) FileUtils.changeValue(programLanguage, programService.findProgramLanguageById(1));
					programType = (ProgramType) FileUtils.changeValue(programType, programService.findProgramTypeById(1));

					Program program = new Program(name, description, periodicity, duration, accounts, accountPayer, programType, programThematic,
							programCategory, programLanguage, email, twitter, facebook, podcast, web);
					programService.save(program);
					program.setDateCreate((Date) FileUtils.changeValue(program.getDateCreate(), dateCreate));
					program.setActive((Boolean) FileUtils.changeValue(true, active));
					programService.update(program);
				}
			} catch (Exception e) {
				logger.error("parser, error program: " + name + " - ", e);
			}
		}

	}

	/**
	 * Gets the members.
	 *
	 * @param name the name
	 * @return the members
	 */
	private List<Account> getMembers(String name) {
		List<Account> accounts = new ArrayList<>();

		for (Account account : accountService.getAccounts()) {
			String[] programs = account.getProgramName().split(",");
			for (String program : programs) {
				if (name.trim().equalsIgnoreCase(program.trim())) {
					accounts.add(account);
				}
			}
		}
		return accounts;
	}
}
