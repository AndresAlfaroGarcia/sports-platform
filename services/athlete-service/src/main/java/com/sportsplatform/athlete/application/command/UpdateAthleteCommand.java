package com.sportsplatform.athlete.application.command;

import com.sportsplatform.athlete.domain.model.Gender;

import java.time.LocalDate;

public record UpdateAthleteCommand(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        Gender gender
) { }