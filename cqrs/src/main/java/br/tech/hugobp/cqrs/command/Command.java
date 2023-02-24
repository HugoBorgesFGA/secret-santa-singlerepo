package br.tech.hugobp.cqrs.command;

import br.tech.hugobp.cqrs.Message;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Command extends Message {
    protected Command(Serializable data, String entityId) {
        super(data, entityId);
    }

    protected Command(Serializable data) {
        super(data);
    }
}
