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
package org.cuacfm.members.web.feemember;

import java.util.List;

import org.cuacfm.members.model.feemember.FeeMember;
import org.cuacfm.members.model.feememberservice.FeeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/** The Class FeeMemberListController. */
@Controller
public class FeeMemberListController {

	private static final String FEEMEMBER_VIEW_NAME = "feemember/feememberlist";

	@Autowired
	private FeeMemberService feeMemberService;

	/** The feeMembers. */
	private List<FeeMember> feeMembers;

	/**
	 * Instantiates a new fee member List Controller.
	 */
	public FeeMemberListController() {
		// Default empty constructor.
	}

	/**
	 * List of FeeMember.
	 *
	 * @return List<FeeMember>
	 */
	@ModelAttribute("feeMembers")
	public List<FeeMember> feeMembers() {
		return feeMembers;
	}

	/**
	 * Show FeeMember List.
	 *
	 * @param model the model
	 * @return the string the view
	 */

	@RequestMapping(value = "feeMemberList")
	public String feeMembers(Model model) {
		feeMembers = feeMemberService.getFeeMemberList();
		model.addAttribute("feeMembers", feeMembers);
		return FEEMEMBER_VIEW_NAME;
	}

}
