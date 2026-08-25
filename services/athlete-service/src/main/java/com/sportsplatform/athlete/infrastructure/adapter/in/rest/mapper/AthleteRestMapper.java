package com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.CreateAthleteRequest;

public class AthleteRestMapper {

    public AthleteResponse toResponse(Athlete athlete) {
        return new AthleteResponse(
                athlete.getId(),
                athlete.getFirstName(),
                athlete.getLastName(),
                athlete.getEmail(),
                athlete.getBirthDate(),
                athlete.getGender(),
                athlete.isActive()
        );
    }

    public CreateAthleteCommand toCommand(CreateAthleteRequest request) {
        return new CreateAthleteCommand(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.birthDate(),
                request.gender()
        );
    }
}