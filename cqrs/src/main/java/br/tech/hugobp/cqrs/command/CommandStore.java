package br.tech.hugobp.cqrs.command;

public interface CommandStore {
    void storeSuccessfulCommand(Command executedCommand);
    void storeFailedCommand(Command executedCommand, Throwable error);
}
