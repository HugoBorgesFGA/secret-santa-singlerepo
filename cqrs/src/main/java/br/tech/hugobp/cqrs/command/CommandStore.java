package br.tech.hugobp.cqrs.command;

import br.tech.hugobp.cqrs.command.exceptions.BusinessException;

public interface CommandStore {
    void storeSuccessfulCommand(Command executedCommand);
    void storeFailedCommand(Command executedCommand, BusinessException error);
}
