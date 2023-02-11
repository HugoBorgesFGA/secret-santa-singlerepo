package br.tech.hugobp.cqrs.command.exceptions;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String shortMessageDescription) {
        super(shortMessageDescription);
    }
}
