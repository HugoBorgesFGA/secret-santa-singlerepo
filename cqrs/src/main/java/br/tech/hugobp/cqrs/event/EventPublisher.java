package br.tech.hugobp.cqrs.event;

public interface EventPublisher {
    void publish(Event event);
}
