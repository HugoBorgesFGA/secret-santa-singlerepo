package br.tech.hugobp.cqrs.command;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Command {

    private final String id;
    private final LocalDateTime createdAt;

    public Command() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public String getCommandId() {
        return Command.createCommandId(this.getClass());
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static String createCommandId(Class<? extends Command> commandClass) {
        return commandClass.getSimpleName();
    }
}
