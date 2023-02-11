package br.tech.hugobp.cqrs.command.exceptions;

public class CommandProcessingException extends RuntimeException {
    public CommandProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
