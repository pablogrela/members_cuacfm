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
package org.cuacfm.members.model.report;

import java.util.List;

import org.cuacfm.members.model.account.Account;

/**
 * The Interface ReportRepository.
 */
public interface ReportRepository {

	/**
	 * Save.
	 *
	 * @param report the report
	 * @return report
	 */
	public Report save(Report report);

	/**
	 * Update.
	 *
	 * @param report the report
	 * @return report
	 */
	public Report update(Report report);

	/**
	 * Delete.
	 *
	 * @param report the report
	 */
	public void delete(Report report);

	/**
	 * Find by id.
	 *
	 * @param id the id of report
	 * @return the report
	 */
	public Report findById(Long id);

	/**
	 * Find by login.
	 *
	 * @param name the name of report
	 * @return Report
	 */
	public Report findByName(String name);

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
	 * Gets the report list close.
	 *
	 * @return the report list close
	 */
	public List<Report> getReportListClose();

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

}
