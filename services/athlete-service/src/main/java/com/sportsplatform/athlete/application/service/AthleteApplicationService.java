package com.sportsplatform.athlete.application.service;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.command.UpdateAthleteCommand;
import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.application.port.in.CreateAthleteUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthleteByIdUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthletesUseCase;
import com.sportsplatform.athlete.application.port.in.UpdateAthleteUseCase;
import com.sportsplatform.athlete.application.port.out.AthleteRepositoryPort;
import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.UUID;

public class AthleteApplicationService implements CreateAthleteUseCase,
                                                    GetAthleteByIdUseCase,
                                                    GetAthletesUseCase,
                                                    UpdateAthleteUseCase {

    private final AthleteRepositoryPort athleteRepositoryPort;

    public AthleteApplicationService(
            AthleteRepositoryPort athleteRepositoryPort) {
        this.athleteRepositoryPort = athleteRepositoryPort;
    }

    @Override
    public Athlete create(CreateAthleteCommand command) {

        Athlete athlete = Athlete.create(command.firstName(),
                command.lastName(),
                command.email(),
                command.birthDate(),
                command.gender()
        );

        return athleteRepositoryPort.save(athlete);
    }

    @Override
    public Athlete getById(UUID athleteId) {

        return athleteRepositoryPort.findById(athleteId)
                .orElseThrow(() ->
                        new AthleteNotFoundException(athleteId));
    }

    @Override
    public PageResult<Athlete> getAll(PageQuery pageQuery) {
        return athleteRepositoryPort.findAll(pageQuery);
    }

    @Override
    public Athlete update(
            UUID athleteId,
            UpdateAthleteCommand command) {

        Athlete athlete = athleteRepositoryPort.findById(athleteId)
                .orElseThrow(() ->
                        new AthleteNotFoundException(athleteId));

        athlete.update(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.birthDate(),
                command.gender()
        );

        return athleteRepositoryPort.save(athlete);
    }
}