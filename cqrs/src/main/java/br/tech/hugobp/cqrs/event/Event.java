package br.tech.hugobp.cqrs.event;

import br.tech.hugobp.cqrs.Message;
import br.tech.hugobp.cqrs.command.Command;

import java.io.Serializable;

public abstract class Event extends Message {
    protected Event(Serializable data, String entityId) {
        super(data, entityId);
    }

    protected Event(Serializable data) {
        super(data);
    }

    protected Event(Command command) {
        super(command.getData(), command.getEntityId());
    }
}
