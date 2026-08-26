package com.sportsplatform.athlete.application.port.in;

import java.util.UUID;

public interface DeactivateAthleteUseCase {

    void deactivate(UUID athleteId);
}