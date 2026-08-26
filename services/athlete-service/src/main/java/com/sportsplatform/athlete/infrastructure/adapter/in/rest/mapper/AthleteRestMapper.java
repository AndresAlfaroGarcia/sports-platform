package com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.CreateAthleteRequest;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.PagedAthleteResponse;

import java.util.List;

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

    public PagedAthleteResponse toPagedResponse(PageResult<Athlete> pageResult) {

        List<AthleteResponse> content = pageResult.content()
                .stream()
                .map(this::toResponse)
                .toList();

        return new PagedAthleteResponse(
                content,
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements(),
                pageResult.totalPages()
        );
    }
}