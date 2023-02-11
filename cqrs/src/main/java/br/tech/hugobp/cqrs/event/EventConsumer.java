package br.tech.hugobp.cqrs.event;

public interface EventConsumer {
    void handleEvent(Event event);
}
