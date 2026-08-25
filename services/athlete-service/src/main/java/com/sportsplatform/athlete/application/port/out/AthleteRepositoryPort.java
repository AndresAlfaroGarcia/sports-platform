package com.sportsplatform.athlete.application.port.out;

import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.UUID;

public interface AthleteRepositoryPort {

    Athlete save(Athlete athlete);

    Athlete get(UUID id);
}