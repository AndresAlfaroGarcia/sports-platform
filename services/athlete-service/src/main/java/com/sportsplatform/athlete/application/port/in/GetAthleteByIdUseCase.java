package com.sportsplatform.athlete.application.port.in;

import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.UUID;

public interface GetAthleteByIdUseCase {

    Athlete getById(UUID athleteId);
}