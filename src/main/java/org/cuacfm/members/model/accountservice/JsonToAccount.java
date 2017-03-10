/**
 * Copyright (C) 2015 Pablo Grela Palleiro (pablogp_9@hotmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cuacfm.members.model.accountservice;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.account.Account.roles;
import org.cuacfm.members.model.util.FileUtils;
import org.cuacfm.members.web.support.DisplayDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class JsonToAccount.
 */
@Service("jsonToAccount")
public class JsonToAccount {

	private static final Logger logger = LoggerFactory.getLogger(JsonToAccount.class);

	private static final int MAX_AREA = 500;
	private static final int MAX_CHARACTERS = 50;
	private static final int MAX_DNI = 10;
	private static final int MAX_NUMBER = 20;

	@Value("${formAccounts}")
	private String formAccounts;

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
		JSONArray solutions = (JSONArray) jsonObject.get(formAccounts);

		Iterator<JSONObject> iterator = solutions.iterator();
		while (iterator.hasNext()) {
			JSONObject jsonObjectRow = iterator.next();

			String dateString = FileUtils.split((String) jsonObjectRow.get("dateCreate"), MAX_CHARACTERS);
			String[] dateConvert = dateString.split("GMT");
			Date dateCreate = DisplayDate.format(dateConvert[0].trim(), "E MMM dd yyyy HH:mm:ss", Locale.ENGLISH);

			String name = FileUtils.split((String) jsonObjectRow.get("name"), MAX_CHARACTERS);
			String surname = FileUtils.split((String) jsonObjectRow.get("surname"), MAX_CHARACTERS);
			String dni = FileUtils.split((String) jsonObjectRow.get("dni"), MAX_DNI).toUpperCase();
			String email = FileUtils.split((String) jsonObjectRow.get("email"), MAX_CHARACTERS);
			String mobile = FileUtils.split((String) jsonObjectRow.get("mobile"), MAX_NUMBER);
			String phone = FileUtils.split((String) jsonObjectRow.get("phone"), MAX_NUMBER);
			String password = FileUtils.split((String) jsonObjectRow.get("password"), MAX_NUMBER);
			String address = FileUtils.split((String) jsonObjectRow.get("address"), MAX_CHARACTERS);
			String login = FileUtils.split((String) jsonObjectRow.get("login"), MAX_CHARACTERS);
			Boolean emitProgram = FileUtils.getBoolean((String) jsonObjectRow.get("emitProgram"));
			Boolean student = FileUtils.getBoolean((String) jsonObjectRow.get("student"));
			String programName = FileUtils.split((String) jsonObjectRow.get("programName"), MAX_CHARACTERS);
			String knowledge = FileUtils.split((String) jsonObjectRow.get("knowledge"), MAX_AREA);
			String personality = FileUtils.split((String) jsonObjectRow.get("personality"), MAX_AREA);

			try {
				Account accountAux = accountService.findByEmail(email);
				if (accountAux != null) {
					accountAux.setName(FileUtils.changeValue(accountAux.getName(), name));
					accountAux.setSurname(FileUtils.changeValue(accountAux.getSurname(), surname));
					accountAux.setDni(FileUtils.changeValue(accountAux.getDni(), dni));
					accountAux.setAddress(FileUtils.changeValue(accountAux.getAddress(), address));
					accountAux.setLogin(FileUtils.changeValue(accountAux.getLogin(), login));
					accountAux.setEmail(FileUtils.changeValue(accountAux.getEmail(), email));
					accountAux.setPhone(FileUtils.changeValue(accountAux.getPhone(), phone));
					accountAux.setMobile(FileUtils.changeValue(accountAux.getMobile(), mobile));
					accountAux.setPassword(FileUtils.changeValue(accountAux.getPassword(), password));
					accountAux.setProgramName(FileUtils.changeValue(accountAux.getProgramName(), programName));
					accountAux.setStudent((boolean) FileUtils.changeValue(accountAux.isStudent(), student));
					accountAux.setEmitProgram((boolean) FileUtils.changeValue(accountAux.isEmitProgram(), emitProgram));
					accountAux.setPersonality(FileUtils.changeValue(accountAux.getPersonality(), personality));
					accountAux.setKnowledge(FileUtils.changeValue(accountAux.getKnowledge(), knowledge));
					accountAux.setDateCreate((Date) FileUtils.changeValue(accountAux.getDateCreate(), dateCreate));
					accountService.update(accountAux, false, false);

				} else {
					login = FileUtils.changeValue(name + " " + surname, login);
					password = FileUtils.changeValue("123456", password);
					emitProgram = (Boolean) FileUtils.changeValue(emitProgram, false);
					student = (Boolean) FileUtils.changeValue(student, false);

					Account account = new Account(name, surname, dni, address, login, email, phone, mobile, password, roles.ROLE_USER, programName,
							student, emitProgram, personality, knowledge);
					accountService.save(account);
					account.setDateCreate((Date) FileUtils.changeValue(account.getDateCreate(), dateCreate));
					accountService.update(accountAux, false, false);
				}
			} catch (Exception e) {
				// 47384273H, dni repetido
				logger.error("parser, error account: " + email + " - ", e);
			}
		}

	}

}