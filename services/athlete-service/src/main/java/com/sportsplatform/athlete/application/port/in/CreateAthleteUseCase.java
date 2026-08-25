package com.sportsplatform.athlete.application.port.in;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.domain.model.Athlete;

public interface CreateAthleteUseCase {

    /**/
    Athlete create(CreateAthleteCommand command);

}