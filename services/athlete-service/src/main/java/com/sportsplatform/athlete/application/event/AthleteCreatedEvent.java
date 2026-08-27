package com.sportsplatform.athlete.application.event;

import java.util.UUID;

public record AthleteCreatedEvent(
        UUID athleteId,
        String firstName,
        String lastName,
        String email
) { }