package com.sportsplatform.athlete.infrastructure.adapter.in.rest;

import com.sportsplatform.athlete.application.model.PageQuery;
import com.sportsplatform.athlete.application.model.PageResult;
import com.sportsplatform.athlete.application.port.in.CreateAthleteUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthleteByIdUseCase;
import com.sportsplatform.athlete.application.port.in.GetAthletesUseCase;
import com.sportsplatform.athlete.domain.model.Athlete;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.AthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.CreateAthleteRequest;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto.PagedAthleteResponse;
import com.sportsplatform.athlete.infrastructure.adapter.in.rest.mapper.AthleteRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/athletes")
public class AthleteController {

    private final CreateAthleteUseCase createAthleteUseCase;
    private final GetAthleteByIdUseCase getAthleteByIdUseCase;
    private final GetAthletesUseCase getAthletesUseCase;
    private final AthleteRestMapper mapper;

    public AthleteController(
            CreateAthleteUseCase createAthleteUseCase,
            GetAthleteByIdUseCase getAthleteByIdUseCase,
            GetAthletesUseCase getAthletesUseCase,
            AthleteRestMapper mapper) {

        this.createAthleteUseCase = createAthleteUseCase;
        this.getAthleteByIdUseCase = getAthleteByIdUseCase;
        this.getAthletesUseCase = getAthletesUseCase;
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

    @GetMapping(value="/{athleteId}", produces = { "application/json", "application/xml" })
    public AthleteResponse getById(@PathVariable UUID athleteId) {

        Athlete athlete = getAthleteByIdUseCase.getById(athleteId);

        return mapper.toResponse(athlete);
    }

    @GetMapping
    public PagedAthleteResponse getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageQuery query = new PageQuery(page, size);

        PageResult<Athlete> result =
                getAthletesUseCase.getAll(query);

        return mapper.toPagedResponse(result);
    }
}