package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.cqrs.event.EventStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostgresEventStore implements EventStore {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public void store(Event event) {
        final EventEntity eventEntity = eventMapper.from(event);
        eventRepository.save(eventEntity);
    }
}
