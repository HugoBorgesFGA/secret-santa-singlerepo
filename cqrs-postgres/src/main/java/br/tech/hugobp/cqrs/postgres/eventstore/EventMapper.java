package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.command.exceptions.CommandProcessingException;
import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.cqrs.postgres.utils.NonRepeatableIdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventMapper {

    private final ObjectMapper objectMapper;

    public EventEntity from(Event event) {
        try {
            final EventEntity eventEntity = EventEntity.builder()
                .createdAt(event.getCreatedAt())
                .name(event.getName())
                .eventId(event.getId())
                .entityId(event.getEntityId())
                .data(objectMapper.writeValueAsString(event.getData()))
                .build();

            NonRepeatableIdGenerator.stampAsNonRepeatable(eventEntity);
            return eventEntity;
        } catch (JsonProcessingException e) {
            throw new CommandProcessingException("Could not persist event", e);
        }
    }
}
