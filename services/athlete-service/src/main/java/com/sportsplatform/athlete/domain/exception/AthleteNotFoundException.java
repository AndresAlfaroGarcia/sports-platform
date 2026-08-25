package com.sportsplatform.athlete.domain.exception;

import java.util.UUID;

public class AthleteNotFoundException extends RuntimeException {

    public AthleteNotFoundException(UUID athleteId) {
        super("Athlete not found: " + athleteId);
    }
}