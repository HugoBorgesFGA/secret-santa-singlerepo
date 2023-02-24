package br.tech.hugobp.cqrs;

import java.util.UUID;

public abstract class Entity {
    protected final String id;

    protected Entity() {
        this.id = newId();
    }

    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static String newId() {
        return UUID.randomUUID().toString();
    }
}
