package com.sportsplatform.athlete.infrastructure.adapter.in.rest.exception;

import com.sportsplatform.athlete.domain.exception.AthleteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AthleteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAthleteNotFound(
            AthleteNotFoundException exception) {

        return Map.of(
                "error", "ATHLETE_NOT_FOUND",
                "message", exception.getMessage()
        );
    }
}