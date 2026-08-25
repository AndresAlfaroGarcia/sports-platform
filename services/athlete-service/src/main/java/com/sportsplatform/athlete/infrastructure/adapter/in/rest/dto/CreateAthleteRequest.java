package com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto;

import com.sportsplatform.athlete.domain.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateAthleteRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        @NotBlank
        String email,

        @NotNull
        LocalDate birthDate,

        @NotNull
        Gender gender
) {}