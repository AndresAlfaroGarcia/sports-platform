package com.sportsplatform.athlete.infrastructure.adapter.out.persistence.messaging;

import com.sportsplatform.athlete.application.event.AthleteCreatedEvent;
import com.sportsplatform.athlete.application.port.out.AthleteEventPublisherPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAthleteEventPublisherAdapter
        implements AthleteEventPublisherPort {

    private static final String TOPIC = "athlete-events";

    private final KafkaTemplate<String, AthleteCreatedEvent> kafkaTemplate;

    public KafkaAthleteEventPublisherAdapter(
            KafkaTemplate<String, AthleteCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishAthleteCreated(AthleteCreatedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                event.athleteId().toString(),
                event
        );
    }
}