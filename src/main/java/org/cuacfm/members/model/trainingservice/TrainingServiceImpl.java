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
package org.cuacfm.members.model.trainingservice;

import java.util.Date;
import java.util.List;

import org.cuacfm.members.model.account.Account;
import org.cuacfm.members.model.eventservice.EventService;
import org.cuacfm.members.model.exceptions.DateLimitException;
import org.cuacfm.members.model.exceptions.DateLimitExpirationException;
import org.cuacfm.members.model.exceptions.ExistInscriptionsException;
import org.cuacfm.members.model.exceptions.MaximumCapacityException;
import org.cuacfm.members.model.exceptions.UniqueException;
import org.cuacfm.members.model.exceptions.UnsubscribeException;
import org.cuacfm.members.model.exceptions.UserAlreadyJoinedException;
import org.cuacfm.members.model.inscription.Inscription;
import org.cuacfm.members.model.inscription.InscriptionRepository;
import org.cuacfm.members.model.training.Training;
import org.cuacfm.members.model.training.TrainingRepository;
import org.cuacfm.members.model.trainingtype.TrainingType;
import org.cuacfm.members.model.trainingtypeservice.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The Class TrainingService. */
@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingRepository trainingRepository;

	@Autowired
	private TrainingTypeService trainingTypeService;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	@Autowired
	private EventService eventService;

	/** Instantiates a new training service. */
	public TrainingServiceImpl() {
		// Default empty constructor.
	}

	@Override
	public Training save(Training training) throws DateLimitException, UniqueException {
		Object[] arguments = { training.getName() };

		if (training.getDateTraining().before(training.getDateLimit())) {
			eventService.save("training.dateLimit.error", null, 2, arguments);
			throw new DateLimitException(training.getDateLimit(), training.getDateTraining());
		}
		// Update dependecy
		TrainingType trainingType = training.getTrainingType();
		trainingType.setHasTrainings(true);
		trainingTypeService.update(trainingType);

		eventService.save("training.successCreate", null, 2, arguments);
		return trainingRepository.save(training);
	}

	@Override
	public Training update(Training training) throws DateLimitException {
		Object[] arguments = { training.getName() };

		if (training.getDateTraining().before(training.getDateLimit())) {
			eventService.save("training.dateLimit.error", null, 2, arguments);
			throw new DateLimitException(training.getDateLimit(), training.getDateTraining());
		}

		eventService.save("training.successModify", null, 2, arguments);
		return trainingRepository.update(training);
	}

	@Override
	public void delete(Training training) throws ExistInscriptionsException, UniqueException {
		Object[] arguments = { training.getName() };

		// If Exist Dependencies Inscriptions
		if (!inscriptionRepository.getInscriptionListByTrainingId(training.getId()).isEmpty()) {
			eventService.save("training.existDependenciesTrainingsException", null, 2, arguments);
			throw new ExistInscriptionsException();
		}

		TrainingType trainingType = training.getTrainingType();

		// Delete training
		trainingRepository.delete(training);

		// Update dependencies
		List<Training> trainings = trainingRepository.getTrainingListByTrainingTypeId(trainingType.getId());
		if (trainings.isEmpty()) {
			trainingType.setHasTrainings(false);
			trainingTypeService.update(trainingType);
		}

		eventService.save("training.successDelete", null, 2, arguments);
	}

	@Override
	public Training findByName(String login) {
		return trainingRepository.findByName(login);
	}

	@Override
	public Training findById(Long id) {
		return trainingRepository.findById(id);
	}

	@Override
	public List<Training> getTrainingList() {
		return trainingRepository.getTrainingList();
	}

	@Override
	public List<Training> getTrainingListOpen() {
		return trainingRepository.getTrainingListOpen();
	}

	@Override
	public List<Training> getTrainingListClose(int year) {
		return trainingRepository.getTrainingListClose(year);
	}

	@Override
	public void createInscription(Account account, Training training)
			throws MaximumCapacityException, DateLimitExpirationException, UserAlreadyJoinedException {

		// Checks if a limit past the deadline
		Date nowDate = new Date();
		if (training.getDateLimit().before(nowDate)) {
			throw new DateLimitExpirationException(training.getName(), nowDate);
		}

		// Check if available space in this training
		int countPlaces = training.getCountPlaces();
		int maxPlaces = training.getMaxPlaces();
		if (countPlaces >= maxPlaces) {
			throw new MaximumCapacityException(maxPlaces);
		}

		// Check if account already inscription
		Inscription inscriptionSearched = inscriptionRepository.findByInscriptionIds(account.getId(), training.getId());
		if (inscriptionSearched != null) {
			throw new UserAlreadyJoinedException(account.getLogin());
		}

		training.setCountPlaces(training.getCountPlaces() + 1);
		trainingRepository.update(training);

		Inscription inscription = new Inscription(account, training);
		inscriptionRepository.save(inscription);
		Object[] arguments = { training.getName() };
		eventService.save("training.successJoin", account, 2, arguments);
	}

	@Override
	public void deleteInscription(Long accountId, Long trainingId) {
		inscriptionRepository.delete(accountId, trainingId);
	}

	@Override
	public void unsubscribeInscription(Account account, Training training) throws UnsubscribeException {

		Inscription inscription = inscriptionRepository.findByInscriptionIds(account.getId(), training.getId());

		if (inscription.isUnsubscribe()) {
			throw new UnsubscribeException(inscription.getTraining().getName());
		}

		training.setCountPlaces(training.getCountPlaces() - 1);
		trainingRepository.update(training);
		inscription.setUnsubscribe(true);
		inscriptionRepository.update(inscription);
		Object[] arguments = { training.getName() };
		eventService.save("training.removeJoin", account, 2, arguments);
	}

	@Override
	public void updateInscription(Inscription inscription) {
		inscriptionRepository.update(inscription);
	}

	@Override
	public Inscription findByInscriptionIds(Long accountId, Long trainingId) {
		return inscriptionRepository.findByInscriptionIds(accountId, trainingId);
	}

	@Override
	public List<Inscription> getInscriptionsByAccountId(Long accountId) {
		return inscriptionRepository.getByAccountId(accountId);
	}

	@Override
	public List<Inscription> getInscriptionsByTrainingId(Long trainingId) {
		return inscriptionRepository.getByTrainingId(trainingId);
	}

	@Override
	public List<Long> getInscriptionsIdsByAccountId(Long accountId) {
		return inscriptionRepository.getIdsByAccountId(accountId);
	}

	@Override
	public List<Long> getUnsubscribeIdsByAccountId(Long accountId) {
		return inscriptionRepository.getUnsubscribeIdsByAccountId(accountId);
	}

	@Override
	public List<String> getUsernamesByInscription(Long trainingId) {
		return inscriptionRepository.getUsernamesByInscription(trainingId);
	}
}
