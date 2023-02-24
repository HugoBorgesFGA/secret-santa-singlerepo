package br.tech.hugobp.cqrs;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class Message {

    protected LocalDateTime createdAt;
    protected String name;
    protected String id;
    protected String entityId;
    protected Serializable data;

    public Message() {
    }

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
