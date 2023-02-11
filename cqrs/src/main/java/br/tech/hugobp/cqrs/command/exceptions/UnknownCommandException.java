package br.tech.hugobp.cqrs.command.exceptions;

import br.tech.hugobp.cqrs.command.Command;

public class UnknownCommandException extends BusinessException {
    public UnknownCommandException(Command command) {
        super(String.format("Unknown command id: %s (%s)", command.getName(), command.getId()));
    }
}
