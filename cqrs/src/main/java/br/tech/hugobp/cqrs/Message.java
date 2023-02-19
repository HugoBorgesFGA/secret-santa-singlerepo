package br.tech.hugobp.cqrs;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Message {

    protected final LocalDateTime createdAt;
    protected final String name;
    protected final String id;
    protected final String entityId;
    protected final Serializable data;

    public Message(Serializable data, String entityId) {
        this.createdAt = LocalDateTime.now();
        this.name = Message.createMessageName(getClass());
        this.id = UUID.randomUUID().toString();
        this.entityId = entityId;
        this.data = data;
    }

    public Message(Serializable data) {
        this(data, UUID.randomUUID().toString());
    }

    public static String createMessageName(Class<? extends Message> storeableClass) {
        return storeableClass.getSimpleName();
    }
}
