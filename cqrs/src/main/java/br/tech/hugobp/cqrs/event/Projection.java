package br.tech.hugobp.cqrs.event;

import br.tech.hugobp.cqrs.Entity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class Projection<Aggregate extends Entity> {

    private final EventStore eventStore;

    public Aggregate get(String entityId) {
        final List<Event> events = eventStore.getEvents(entityId);
        return handle(events);
    }

    protected Aggregate handle(List<Event> events) {
        Aggregate aggregate = null;
        for(Event event : events) {
            aggregate = handleEvent(aggregate, event);
        }

        return aggregate;
    }

    protected abstract Aggregate handleEvent(Aggregate aggregate, Event event);
}
