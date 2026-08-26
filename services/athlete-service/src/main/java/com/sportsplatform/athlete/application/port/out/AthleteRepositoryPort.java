package com.sportsplatform.athlete.application.port.out;

import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.Optional;
import java.util.UUID;

public interface AthleteRepositoryPort {

    Athlete save(Athlete athlete);

    Optional<Athlete> findById(UUID athleteId);

    PageResult<Athlete> findAll(PageQuery pageQuery);
}