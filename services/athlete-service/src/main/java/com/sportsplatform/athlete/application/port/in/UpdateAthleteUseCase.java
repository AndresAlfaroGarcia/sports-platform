package com.sportsplatform.athlete.application.port.in;

import com.sportsplatform.athlete.application.command.UpdateAthleteCommand;
import com.sportsplatform.athlete.domain.model.Athlete;

import java.util.UUID;

public interface UpdateAthleteUseCase {

    Athlete update(
            UUID athleteId,
            UpdateAthleteCommand command
    );
}