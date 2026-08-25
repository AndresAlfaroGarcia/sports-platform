package com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto;

import com.sportsplatform.athlete.domain.model.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record AthleteResponse(

        UUID id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        Gender gender,
        boolean active
) {}