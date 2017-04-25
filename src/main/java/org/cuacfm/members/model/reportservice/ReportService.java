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

import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.exceptions.ExistPaymentsException;
import org.cuacfm.members.model.report.Report;
import org.cuacfm.members.model.report.ReportDTO;
import org.springframework.web.multipart.MultipartFile;

/** The Class ReportService. */
public interface ReportService {

	/**
	 * Save an training into database.
	 *
	 * @param report the training
	 * @param files the files
	 * @return Report
	 */
	public Report save(Report report, MultipartFile[] files);

	/**
	 * Save.
	 *
	 * @param report the report
	 * @param files the files
	 * @return the report
	 */
	public Report save(Report report, List<byte[]> files);

	/**
	 * Update Report.
	 *
	 * @param report the report
	 * @return Report
	 */
	public Report update(Report report);

	/**
	 * Delete.
	 *
	 * @param report the report
	 */
	public void delete(Report report);

	/**
	 * Delete.
	 *
	 * @param report the report
	 * @param account the account
	 * @throws ExistPaymentsException the exist payments exception
	 */
	public void delete(Report report, Account account) throws ExistPaymentsException;

	/**
	 * Find by Name the report.
	 *
	 * @param name the name
	 * @return Report
	 */
	public Report findByName(String name);

	/**
	 * Find by id returns report which has this identifier.
	 *
	 * @param id the id
	 * @return report
	 */
	public Report findById(Long id);

	/**
	 * Get all reports.
	 *
	 * @return List<Report>
	 */
	public List<Report> getReportList();

	/**
	 * Get all active reports.
	 *
	 * @return List<Report>
	 */
	public List<Report> getReportListActive();

	/**
	 * Gets the report list by user.
	 *
	 * @param account the account
	 * @return the report list by user
	 */
	public List<Report> getReportListByUser(Account account);

	/**
	 * Gets the report list by user.
	 *
	 * @param account the account
	 * @return the report list by user
	 */
	public List<Report> getReportListActiveByUser(Account account);

	/**
	 * Gets the report list close.
	 *
	 * @return the report list close
	 */
	public List<Report> getReportListClose();

	/**
	 * Up.
	 *
	 * @param report the report
	 */
	public void up(Report report);

	/**
	 * Down.
	 *
	 * @param report the report
	 */
	public void down(Report report);

	/**
	 * Answer.
	 *
	 * @param report the report
	 * @param account the account
	 * @param answer the answer
	 * @return the report
	 */
	public Report answer(Report report, Account account, String answer);

	/**
	 * Gets the reports DTO.
	 *
	 * @param reports the reports
	 * @return the reports DTO
	 */
	public List<ReportDTO> getReportsDTO(List<Report> reports);

	/**
	 * Gets the report DTO.
	 *
	 * @param report the report
	 * @return the report DTO
	 */
	public ReportDTO getReportDTO(Report report);

	/**
	 * Gets the report.
	 *
	 * @param reportDTO the report DTO
	 * @param account the account
	 * @return the report
	 */
	public Report getReport(ReportDTO reportDTO, Account account);
}
