package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandRepository {

    private final String commandTable;
    private final PostgresDatabaseConfig databaseConfig;

    public void save(CommandEntity commandEntity) {

    }
}
