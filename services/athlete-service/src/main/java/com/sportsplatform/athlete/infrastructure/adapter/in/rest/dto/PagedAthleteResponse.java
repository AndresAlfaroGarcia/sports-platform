package com.sportsplatform.athlete.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record PagedAthleteResponse(
        List<AthleteResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}