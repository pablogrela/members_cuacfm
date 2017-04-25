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
package org.cuacfm.members.model.reportservice;

import java.io.IOException;
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
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.cuacfm.members.model.report.ReportRepository;
import org.cuacfm.members.model.util.Constants.levels;
import org.cuacfm.members.model.util.DateUtils;
import org.cuacfm.members.model.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** The Class ReportServiceImpl. */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

	private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Value("${path}${pathReports}")
	private String pathReports;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new report service. */
	public ReportServiceImpl() {
		super();
	}

	@Override
	public Report save(Report report, MultipartFile[] filesMultipart) {
		logger.info("save report, convert MultipartFile");

		List<byte[]> files = new ArrayList<>();
		if (filesMultipart != null && filesMultipart.length > 0 && filesMultipart[0].getSize() > 0) {
			for (MultipartFile file : filesMultipart) {
				try {
					files.add(file.getBytes());
					// String extension = file.getOriginalFilename().split("\\.")[1]
					// String originalFilename = file.getOriginalFilename().split("\\.")[0]	
				} catch (IOException e) {
					logger.error("save: ", e);
				}
			}
		}
		return save(report, files);
	}

	@Override
	public Report save(Report report, List<byte[]> filesByte) {
		logger.info("save report");

		// Create files in path
		try {
			if (filesByte != null && !filesByte.isEmpty()) {
				String date = DateUtils.format(new Date(), DateUtils.FORMAT_FILE);
				String finalPath = pathReports + report.getProgram().getName() + "/" + date + "/";
				FileUtils.createFolderIfNoExist(finalPath);
				report.setFile(finalPath);
				List<String> files = new ArrayList<>();
				int i = 0;
				for (byte[] file : filesByte) {
					String newNameFile = messageSource.getMessage("report", null, Locale.getDefault()) + "_" + i + "_" + date + ".png";
					files.add(newNameFile);
					Path pathFile = Paths.get(finalPath + newNameFile);
					Files.write(pathFile, file);
					i++;
				}
				report.setFiles(files);
			}
		} catch (Exception e) {
			logger.error("save: ", e);
			Object[] arguments = { report.getProgram().getName() };
			eventService.save("program.failUpload", null, levels.HIGH, arguments);
		}

		levels reportLevel = levels.MEDIUM;

		// Add description if level is low
		String description = report.getDescription();
		report.setDescription("");
		if (report.getDirt() < reportLevel.getValue()) {
			report.setDescription(report.getDescription() + messageSource.getMessage("report.dirt.low", null, Locale.getDefault()) + "\n");
		}
		if (report.getTidy() < reportLevel.getValue()) {
			report.setDescription(report.getDescription() + messageSource.getMessage("report.tidy.low", null, Locale.getDefault()) + "\n");
		}
		if (report.getConfiguration() < reportLevel.getValue()) {
			report.setDescription(report.getDescription() + messageSource.getMessage("report.configuration.low", null, Locale.getDefault()) + "\n");
		}
		report.setDescription(report.getDescription() + description);

		// If it accomplish these requirements is active
		if ((report.getDescription() != null && !report.getDescription().isEmpty()) || report.getFile() != null) {
			report.setActive(true);
			reportLevel = levels.CRITICAL;
		}

		// Save Message Event
		Object[] arguments = { report.getProgram().getName() };
		eventService.save("report.create.success", report.getAccount(), reportLevel, arguments);
		return reportRepository.save(report);
	}

	@Override
	public Report update(Report report) {

		// Save Message Event
		Object[] arguments = { report.getProgram().getName() };
		eventService.save("report.edit.success", null, levels.MEDIUM, arguments);
		return reportRepository.update(report);

	}

	@Override
	public void delete(Report report) {
		delete(report, null);
	}

	@Override
	public void delete(Report report, Account account) {
		reportRepository.delete(report);
	}

	@Override
	public Report findByName(String login) {
		return reportRepository.findByName(login);
	}

	@Override
	public Report findById(Long id) {
		return reportRepository.findById(id);
	}

	@Override
	public List<Report> getReportList() {
		return reportRepository.getReportList();
	}

	@Override
	public List<Report> getReportListActive() {
		return reportRepository.getReportListActive();
	}

	@Override
	public List<Report> getReportListByUser(Account account) {
		return reportRepository.getReportListByUser(account);
	}

	@Override
	public List<Report> getReportListActiveByUser(Account account) {
		return reportRepository.getReportListActiveByUser(account);
	}

	@Override
	public List<Report> getReportListClose() {
		return reportRepository.getReportListClose();
	}

	@Override
	public void up(Report report) {
		report.setActive(true);
		reportRepository.update(report);

		Object[] arguments = { report.getProgram().getName() };
		eventService.save("report.up.sucess", null, levels.MEDIUM, arguments);
	}

	@Override
	public void down(Report report) {
		report.setActive(false);
		report.setDateRevision(new Date());
		reportRepository.update(report);

		Object[] arguments = { report.getProgram().getName() };
		eventService.save("report.down.success", null, levels.MEDIUM, arguments);
	}

	@Override
	public Report answer(Report report, Account account, String answer) {
		String log = "";
		if (report.getAnswer() != null) {
			log = report.getAnswer();
		}
		String aux = DateUtils.format(new Date(), DateUtils.FORMAT_DISPLAY) + " - " + account.getName() + "\n";
		report.setAnswer(aux + "\t" + answer + "\n" + log);
		reportRepository.update(report);

		Object[] arguments = { account.getFullName(), report.getProgram().getName() };
		eventService.save("report.answer.user", report.getAccount(), levels.HIGH, arguments);

		return report;
	}

	@Override
	public List<ReportDTO> getReportsDTO(List<Report> reports) {
		List<ReportDTO> reportsDTO = new ArrayList<>();
		for (Report report : reports) {
			reportsDTO.add(getReportDTO(report));
		}
		return reportsDTO;
	}

	@Override
	public ReportDTO getReportDTO(Report report) {
		ReportDTO reportDTO = null;

		if (report != null) {
			reportDTO = new ReportDTO(report.getId(), accountService.getAccountDTO(report.getAccount()),
					programService.getProgramDTO(report.getProgram()), report.getDirt(), report.getTidy(), report.getConfiguration(),
					report.isOpenDoor(), report.isViewMembers(), report.getLocation(), report.getDescription(), report.getFiles(), report.getAnswer(),
					report.getDateCreate(), report.getDateRevision(), report.isActive());
		}
		return reportDTO;
	}

	@Override
	public Report getReport(ReportDTO reportDTO, Account account) {
		if (account == null) {
			account = accountService.findByEmail(reportDTO.getAccount().getEmail());
		}

		return new Report(account, programService.findByName(reportDTO.getProgram().getName()), reportDTO.getDirt(), reportDTO.getTidy(),
				reportDTO.getConfiguration(), reportDTO.isOpenDoor(), reportDTO.isViewMembers(), reportDTO.getLocation(), reportDTO.getDescription());
	}

}
