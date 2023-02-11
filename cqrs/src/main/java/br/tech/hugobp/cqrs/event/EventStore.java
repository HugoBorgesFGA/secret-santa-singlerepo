package br.tech.hugobp.cqrs.event;

public interface EventStore {
    void store(Event event);
}
