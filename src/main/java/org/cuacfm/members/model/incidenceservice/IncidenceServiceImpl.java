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
package org.cuacfm.members.model.incidenceservice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.accountservice.AccountService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.incidence.Incidence;
import org.cuacfm.members.model.incidence.IncidenceDTO;
import org.cuacfm.members.model.incidence.IncidenceRepository;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** The Class IncidenceServiceImpl. */
@Service("incidenceService")
public class IncidenceServiceImpl implements IncidenceService {

	private static final Logger logger = LoggerFactory.getLogger(IncidenceServiceImpl.class);

	@Value("${path}${pathIncidences}")
	private String pathIncidences;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private IncidenceRepository incidenceRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new incidence service. */
	public IncidenceServiceImpl() {
		super();
	}

	@Override
	public Incidence save(Incidence incidence, MultipartFile[] filesMultipart) {

		logger.info("save incidence");

		// Create files in path
		try {
			if (filesMultipart != null && filesMultipart.length > 0 && filesMultipart[0].getSize() > 0) {
				String date = DateUtils.format(new Date(), DateUtils.FORMAT_FILE);
				String finalPath = pathIncidences + incidence.getProgram().getName() + "/" + date + "/";
				FileUtils.createFolderIfNoExist(finalPath);
				incidence.setFile(finalPath);
				List<String> files = new ArrayList<>();
				int i = 0;
				for (MultipartFile file : filesMultipart) {
					// String extension = file.getOriginalFilename().split("\\.")[1]
					// String originalFilename = file.getOriginalFilename().split("\\.")[0]			
					String newNameFile = messageSource.getMessage("incidence", null, Locale.getDefault()) + "_" + i + "_" + date + ".png";
					files.add(newNameFile);
					Path pathFile = Paths.get(finalPath + newNameFile);
					byte[] bytes = file.getBytes();
					Files.write(pathFile, bytes);
					i++;
				}
				incidence.setFiles(files);
			}
		} catch (Exception e) {
			logger.error("save: ", e);
			Object[] arguments = { incidence.getProgram().getName() };
			eventService.save("program.failUpload", null, 2, arguments);
		}

		// Save Message Event
		Object[] arguments = { incidence.getProgram().getName() };
		eventService.save("incidence.create.success", null, 3, arguments);
		return incidenceRepository.save(incidence);
	}

	@Override
	public Incidence update(Incidence incidence) {

		// Save Message Event
		Object[] arguments = { incidence.getProgram().getName() };
		eventService.save("incidence.edit.success", null, 3, arguments);
		return incidenceRepository.update(incidence);

	}

	@Override
	public void delete(Incidence incidence) {
		delete(incidence, null);
	}

	@Override
	public void delete(Incidence incidence, Account account) {
		incidenceRepository.delete(incidence);
	}

	@Override
	public Incidence findByName(String login) {
		return incidenceRepository.findByName(login);
	}

	@Override
	public Incidence findById(Long id) {
		return incidenceRepository.findById(id);
	}

	@Override
	public List<Incidence> getIncidenceList() {
		return incidenceRepository.getIncidenceList();
	}

	@Override
	public List<Incidence> getIncidenceListActive() {
		return incidenceRepository.getIncidenceListActive();
	}

	@Override
	public List<Incidence> getIncidenceListByUser(Account account) {
		return incidenceRepository.getIncidenceListByUser(account);
	}

	@Override
	public List<Incidence> getIncidenceListClose() {
		return incidenceRepository.getIncidenceListClose();
	}

	@Override
	public void up(Incidence incidence) {
		incidence.setActive(true);
		incidenceRepository.update(incidence);

		Object[] arguments = { incidence.getProgram().getName() };
		eventService.save("incidence.up.success", null, 3, arguments);
	}

	@Override
	public void down(Incidence incidence) {
		incidence.setActive(false);
		incidence.setDateRevision(new Date());
		incidenceRepository.update(incidence);

		Object[] arguments = { incidence.getProgram().getName() };
		eventService.save("incidence.down.success", null, 3, arguments);
	}

	@Override
	public void answer(Incidence incidence, String answer) {
		incidence.setAnswer(answer);
		incidenceRepository.update(incidence);

		Object[] arguments = { incidence.getProgram().getName() };
		eventService.save("incidence.answer.admin", incidence.getAccount(), 2, arguments);
	}

	@Override
	public List<IncidenceDTO> getIncidencesDTO(List<Incidence> incidences) {

		List<IncidenceDTO> incidencesDTO = new ArrayList<>();
		for (Incidence incidence : incidences) {
			incidencesDTO.add(getIncidenceDTO(incidence));
		}
		return incidencesDTO;
	}

	@Override
	public IncidenceDTO getIncidenceDTO(Incidence incidence) {
		IncidenceDTO incidenceDTO = null;
		
		if (incidence != null) {
			incidenceDTO = new IncidenceDTO(incidence.getId(), accountService.getAccountDTO(incidence.getAccount()),
					programService.getProgramDTO(incidence.getProgram()), incidence.getDirt(), incidence.getTidy(), incidence.getConfiguration(),
					incidence.isOpenDoor(), incidence.isViewMembers(), incidence.getDescription(), incidence.getFiles(), incidence.getAnswer(),
					incidence.getDateCreate(), incidence.getDateRevision(), incidence.isActive());
		}
		return incidenceDTO;
	}

	@Override
	public Incidence getIncidence(IncidenceDTO incidenceDTO, Account account) {
		if (account == null) {
			account = accountService.findByEmail(incidenceDTO.getAccount().getEmail());
		}
		
		return new Incidence(account, programService.findByName(incidenceDTO.getProgram().getName()), incidenceDTO.getDirt(), incidenceDTO.getTidy(),
				incidenceDTO.getConfiguration(), incidenceDTO.isOpenDoor(), incidenceDTO.isViewMembers(), incidenceDTO.getDescription());
	}

}
