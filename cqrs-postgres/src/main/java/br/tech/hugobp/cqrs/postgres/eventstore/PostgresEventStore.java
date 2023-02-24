package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.cqrs.event.EventStore;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostgresEventStore implements EventStore {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public void store(Event event) {
        final EventEntity eventEntity = eventMapper.toEntity(event);
        eventRepository.save(eventEntity);
    }

    @Override
    public List<Event> getEvents(String entityId) {
        return eventRepository.findAll(entityId).stream()
            .map(eventMapper::toDomain)
            .collect(Collectors.toList());
    }
}
