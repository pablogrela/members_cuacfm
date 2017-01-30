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
package org.cuacfm.members.model.feeprogramservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.directdebitservice.DirectDebitService;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.feeprogram.FeeProgram;
import org.cuacfm.members.model.feeprogram.FeeProgramRepository;
import org.cuacfm.members.model.payprogram.PayProgram;
import org.cuacfm.members.model.payprogramservice.PayProgramService;
import org.cuacfm.members.model.program.Program;
import org.cuacfm.members.model.programservice.ProgramService;
import org.cuacfm.members.model.util.Constants.states;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class FeeProgramServiceImpl. */
@Service("feeProgramService")
public class FeeProgramServiceImpl implements FeeProgramService {

	@Autowired
	private FeeProgramRepository feeProgramRepository;

	@Autowired
	private ProgramService programService;

	@Autowired
	private PayProgramService payProgramService;

	@Autowired
	private DirectDebitService directDebitService;

	@Autowired
	private EventService eventService;

	/** Instantiates a new feeProgram service. */
	public FeeProgramServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public FeeProgram save(FeeProgram feeProgram) throws UniqueException {
		// It is verified that there is not exist year of feeProgram in other feeProgram
		if (feeProgramRepository.findByDate(feeProgram.getDate()) != null) {
			throw new UniqueException("Date", String.valueOf(feeProgram.getDate()));
		}
		feeProgramRepository.save(feeProgram);

		// Create payments of programs
		for (Program program : programService.getProgramListActive()) {
			// Duration in minutes, fee in hours, it is necessary convert price to minutes
			Double price = (feeProgram.getPrice() / 60) * program.getDuration() * program.getPeriodicity() ;
			PayProgram payProgram = new PayProgram(program, feeProgram, price);
			payProgramService.save(payProgram);
			if (program.getAccountPayer() != null){
				directDebitService.save(program.getAccountPayer());
			}
		}

		Object[] arguments = { feeProgram.getName() };
		eventService.save("feeProgram.successCreate", null, 2, arguments);
		return feeProgram;
	}

	@Override
	public FeeProgram update(FeeProgram feeProgram) throws UniqueException {
		// It is verified that there is not exist name of feeProgram in other feeProgram
		FeeProgram feeProgramSearch = feeProgramRepository.findByDate(feeProgram.getDate());
		if ((feeProgramSearch != null) && (feeProgramSearch.getId() != feeProgram.getId())) {
			throw new UniqueException("Date", String.valueOf(feeProgram.getDate()));
		}

		Object[] arguments = { feeProgram.getName() };
		eventService.save("feeProgram.successModify", null, 2, arguments);
		return feeProgramRepository.update(feeProgram);
	}

	@Override
	public FeeProgram refresh(FeeProgram feeProgram) {

		for (PayProgram payProgram : payProgramService.getPayProgramListByFeeProgramId(feeProgram.getId())) {
			// Si el programa esta dado de baja y no esta pagado se elimina
			if (!payProgram.getProgram().isActive() && (payProgram.getState().equals(states.NO_PAY) || payProgram.getState().equals(states.CANCEL))) {
				payProgramService.remove(payProgram);
			}
		}

		// Añadir nuevos pagos si es necesario
		for (Program program : programService.getProgramListActiveWhitoutPays(feeProgram.getDate())) {
			Double price = (feeProgram.getPrice() / 60) * program.getDuration() * program.getPeriodicity();
			PayProgram payProgram = new PayProgram(program, feeProgram, price);
			payProgramService.save(payProgram);
			directDebitService.save(program.getAccountPayer());
		}

		Object[] arguments = { feeProgram.getName() };
		eventService.save("feeProgram.successRefresh", null, 2, arguments);
		return feeProgram;
	}

	@Override
	public FeeProgram findByName(String name) {
		return feeProgramRepository.findByName(name);
	}

	@Override
	public FeeProgram findById(Long id) {
		return feeProgramRepository.findById(id);
	}

	@Override
	public FeeProgram findByDate(Date date) {
		return feeProgramRepository.findByDate(date);
	}

	@Override
	public List<FeeProgram> getFeeProgramList() {
		return feeProgramRepository.getFeeProgramList();
	}
}
