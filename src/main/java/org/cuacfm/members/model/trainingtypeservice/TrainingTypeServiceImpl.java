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
package org.cuacfm.members.model.trainingtypeservice;

import java.util.List;

import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.ExistTrainingsException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.training.TrainingRepository;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtype.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class TrainingTypeServiceImpl. */
@Service("trainingTypeService")
public class TrainingTypeServiceImpl implements TrainingTypeService {

	@Autowired
	private TrainingTypeRepository trainingTypeRepository;

	@Autowired
	private TrainingRepository trainingRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new trainingType service. */
	public TrainingTypeServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public TrainingType save(TrainingType trainingType) throws UniqueException {
		// It is verified that there is not exist name of trainingType in other trainingType
		Object[] arguments = { trainingType.getName() };

		if (trainingTypeRepository.findByName(trainingType.getName()) != null) {
			eventService.save("trainingType.existentName", null, 2, arguments);
			throw new UniqueException("Name", trainingType.getName());
		}

		eventService.save("trainingType.successCreate", null, 2, arguments);
		return trainingTypeRepository.save(trainingType);
	}

	@Override
	public TrainingType update(TrainingType trainingType) throws UniqueException {
		// It is verified that there is not exist name of trainingType in other trainingType
		Object[] arguments = { trainingType.getName() };

		TrainingType trainingTypeSearch = trainingTypeRepository.findByName(trainingType.getName());
		if ((trainingTypeSearch != null) && (trainingTypeSearch.getId() != trainingType.getId())) {
			eventService.save("trainingType.existentName", null, 2, arguments);
			throw new UniqueException("Name", trainingType.getName());
		}

		eventService.save("trainingType.successModify", null, 2, arguments);
		return trainingTypeRepository.update(trainingType);
	}

	@Override
	public void delete(TrainingType trainingType) throws ExistTrainingsException {
		// If Exist Dependencies Trainings
		Object[] arguments = { trainingType.getName() };

		if (!trainingRepository.getTrainingListByTrainingTypeId(trainingType.getId()).isEmpty()) {
			eventService.save("trainingType.existDependenciesTrainings", null, 2, arguments);
			throw new ExistTrainingsException();

		}
		trainingTypeRepository.delete(trainingType.getId());
		eventService.save("trainingType.successDelete", null, 2, arguments);
	}

	@Override
	public TrainingType findByName(String login) {
		return trainingTypeRepository.findByName(login);
	}

	@Override
	public TrainingType findById(Long id) {
		return trainingTypeRepository.findById(id);
	}

	@Override
	public List<TrainingType> getTrainingTypeList() {
		return trainingTypeRepository.getTrainingTypeList();
	}

	@Override
	public List<Training> getTrainingListByTrainingTypeId(Long trainingTypeId) {
		return trainingRepository.getTrainingListByTrainingTypeId(trainingTypeId);
	}
}
