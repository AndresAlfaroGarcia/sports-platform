package com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto;

import com.sportsplatform.athlete.domain.model.Gender;

import java.time.LocalDate;

public record UpdateAthleteRequest(
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        Gender gender
) {
}