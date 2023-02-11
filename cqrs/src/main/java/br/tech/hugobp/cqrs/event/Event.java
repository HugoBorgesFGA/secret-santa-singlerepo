package br.tech.hugobp.cqrs.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Event {
    protected final String id;
    protected final String entityId;
    protected final LocalDateTime createdAt;

    protected Event(String entityId) {
        this.id = UUID.randomUUID().toString();
        this.entityId = entityId;
        this.createdAt = LocalDateTime.now();
    }

    public String getEventType() {
        return Event.createEventType(this.getClass());
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEntityId() {
        return entityId;
    }

    public static String createEventType(Class<? extends Event> commandClass) {
        return commandClass.getSimpleName();
    }
}
