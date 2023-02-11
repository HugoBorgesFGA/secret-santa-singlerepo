package br.tech.hugobp.cqrs.command;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public abstract class Command<Data extends Serializable> {

    private final long createdAt;
    private final String name;
    private final int version;
    private final String id;
    private final String entityId;
    private final Data data;

    public Command(int version, Data data, String entityId) {
        this.createdAt = System.nanoTime();
        this.name = Command.createCommandName(getClass());
        this.version = version;
        this.id = UUID.randomUUID().toString();
        this.entityId = entityId;
        this.data = data;
    }

    public Command(int version, Data data) {
        this(version, data, UUID.randomUUID().toString());
    }

    public static String createCommandName(Class<? extends Command> commandClass) {
        return commandClass.getSimpleName();
    }
}
