package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.command.Command;
import br.tech.hugobp.cqrs.command.CommandStore;
import br.tech.hugobp.cqrs.command.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostgresCommandStore implements CommandStore {

    private final CommandRepository commandRepository;
    private final CommandMapper commandMapper;

    @Override
    public void storeSuccessfulCommand(Command executedCommand) {
        final CommandEntity commandEntity = commandMapper.toEntity(executedCommand, null);
        commandRepository.save(commandEntity);
    }

    @Override
    public void storeFailedCommand(Command executedCommand, BusinessException error) {
        final CommandEntity commandEntity = commandMapper.toEntity(executedCommand, error.getMessage());
        commandRepository.save(commandEntity);
    }
}
