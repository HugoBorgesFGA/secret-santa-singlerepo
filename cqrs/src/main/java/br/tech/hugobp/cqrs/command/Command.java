package br.tech.hugobp.cqrs.command;

import br.tech.hugobp.cqrs.Message;
import lombok.Getter;

import java.io.Serializable;

@Getter
public abstract class Command extends Message {
    public Command(Serializable data, String entityId) {
        super(data, entityId);
    }

    public Command(Serializable data) {
        super(data);
    }
}
