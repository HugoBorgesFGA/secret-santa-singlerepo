package br.tech.hugobp.cqrs.event;

import java.util.List;

public interface EventStore {
    void store(Event event);

    List<Event> getEvents(String entityId);
}
