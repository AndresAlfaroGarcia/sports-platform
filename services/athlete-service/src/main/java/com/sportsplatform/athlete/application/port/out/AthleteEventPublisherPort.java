package com.sportsplatform.athlete.application.port.out;

import com.sportsplatform.athlete.application.event.AthleteCreatedEvent;

public interface AthleteEventPublisherPort {

    void publishAthleteCreated(AthleteCreatedEvent event);
}