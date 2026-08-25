package com.sportsplatform.athlete.infrastructure.adapter.in.rest;

import com.sportsplatform.athlete.application.command.CreateAthleteCommand;
import com.sportsplatform.athlete.application.port.in.CreateAthleteUseCase;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.domain.model.Gender;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.CreateAthleteRequest;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper.AthleteRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/athletes")
public class AthleteController {

    private final CreateAthleteUseCase createAthleteUseCase;
    private final AthleteRestMapper mapper;

    public AthleteController(
            CreateAthleteUseCase createAthleteUseCase,
            AthleteRestMapper mapper) {

        this.createAthleteUseCase = createAthleteUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AthleteResponse create(
            @Valid @RequestBody CreateAthleteRequest request) {

        Athlete athlete = createAthleteUseCase.create(
                mapper.toCommand(request)
        );

        return mapper.toResponse(athlete);
    }

    @GetMapping(value="{id}", produces = { "application/json", "application/xml" })
    public AthleteResponse get(@PathVariable UUID id) {

        Athlete athlete = createAthleteUseCase.get(id);

        return mapper.toResponse(athlete);
    }}