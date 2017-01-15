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
package org.cuacfm.members.model.programservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.program.ProgramCategory;
import org.cuacfm.members.model.program.ProgramDTO;
import org.cuacfm.members.model.program.ProgramLanguage;
import org.cuacfm.members.model.program.ProgramRepository;
import org.cuacfm.members.model.program.ProgramThematic;
import org.cuacfm.members.model.program.ProgramType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class ProgramServiceImpl. */
@Service("programService")
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new program service. */
	public ProgramServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public Program save(Program program) throws UniqueException {
		// It is verified that there is not exist name of program in other program
		if (programRepository.findByName(program.getName()) != null) {
			throw new UniqueException("Name", program.getName());
		}

		// Save Message Event
		Object[] arguments = { program.getName() };
		eventService.save("program.successCreate", null, 3, arguments);
		return programRepository.save(program);
	}

	@Override
	public Program update(Program program) throws UniqueException {
		// It is verified that there is not exist name of program in other program
		Program programSearch = programRepository.findByName(program.getName());
		if ((programSearch != null) && (programSearch.getId() != program.getId())) {
			throw new UniqueException("Name", program.getName());
		}

		// Save Message Event
		Object[] arguments = { program.getName() };
		eventService.save("program.successModify", null, 3, arguments);
		return programRepository.update(program);

	}

	@Override
	public void delete(Program program) throws ExistPaymentsException {
		delete(program, null);
	}

	@Override
	public void delete(Program program, Account account) throws ExistPaymentsException {
		// If Exist payments
		if (!payProgramService.getPayProgramListByProgramId(program.getId()).isEmpty()) {
			throw new ExistPaymentsException();
		}
		programRepository.delete(program);

		Object[] arguments = { program.getName() };
		eventService.save("program.successDelete", account, 3, arguments);
	}

	@Override
	public Program findByName(String login) {
		return programRepository.findByName(login);
	}

	@Override
	public Program findById(Long id) {
		return programRepository.findById(id);
	}

	@Override
	public List<Program> getProgramList() {
		return programRepository.getProgramList();
	}

	@Override
	public List<Program> getProgramListActive() {
		return programRepository.getProgramListActive();
	}

	@Override
	public List<Program> getProgramListActiveWhitoutPays(Date month){
		return programRepository.getProgramListActiveWhitoutPays(month);
	}
	
	@Override
	public void up(Program program) {
		program.setActive(true);
		programRepository.update(program);

		Object[] arguments = { program.getName() };
		eventService.save("program.successUp", null, 3, arguments);
	}

	@Override
	public void down(Program program) {
		program.setActive(false);
		program.setDateDown(new Date());
		programRepository.update(program);

		Object[] arguments = { program.getName() };
		eventService.save("program.successDown", null, 3, arguments);
	}

	@Override
	public List<ProgramDTO> getProgramsDTO(List<Program> programs) {

		List<ProgramDTO> programsDTO = new ArrayList<>();
		for (Program program : programs) {
			programsDTO.add(getProgramDTO(program));
		}
		return programsDTO;
	}

	@Override
	public ProgramDTO getProgramDTO(Program program) {

		ProgramDTO programDTO = new ProgramDTO(program.getId(), program.getName(), program.getDescription(), program.getPeriodicity(),
				program.getDuration(), accountService.getAccountsDTO(program.getAccounts()), accountService.getAccountDTO(program.getAccountPayer()),
				program.getProgramType().getName(), program.getProgramThematic().getName(), program.getProgramLanguage().getName(),
				program.getProgramCategory().getName(), program.getEmail(), program.getTwitter(), program.getFacebook(), program.getPodcast(),
				program.getWeb(), program.isActive(), program.getDateCreate(), program.getDateDown());
		return programDTO;
	}

	@Override
	public List<ProgramType> findProgramTypeList() {
		return programRepository.findProgramTypeList();
	}

	@Override
	public ProgramType findProgramTypeById(int id) {
		return programRepository.findProgramTypeById(id);
	}

	@Override
	public List<ProgramThematic> findProgramThematicList() {
		return programRepository.findProgramThematicList();
	}

	@Override
	public ProgramThematic findProgramThematicById(int id) {
		return programRepository.findProgramThematicById(id);
	}

	@Override
	public List<ProgramCategory> findProgramCategoryList() {
		return programRepository.findProgramCategoryList();
	}

	@Override
	public ProgramCategory findProgramCategoryById(int id) {
		return programRepository.findProgramCategoryById(id);
	}

	@Override
	public List<ProgramLanguage> findProgramLanguageList() {
		return programRepository.findProgramLanguageList();
	}

	@Override
	public ProgramLanguage findProgramLanguageById(int id) {
		return programRepository.findProgramLanguageById(id);
	}

}
