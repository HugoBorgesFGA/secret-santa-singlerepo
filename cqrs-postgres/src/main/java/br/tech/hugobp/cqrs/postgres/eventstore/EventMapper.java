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

    public Event toDomain(EventEntity entity) {
        final Event event = new Event();
        event.setCreatedAt(entity.getCreatedAt());
        event.setName(entity.getName());
        event.setId(entity.getEventId());
        event.setEntityId(entity.getEntityId());
        event.setData(entity.getData());

        return event;
    };

    public EventEntity toEntity(Event event) {
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
