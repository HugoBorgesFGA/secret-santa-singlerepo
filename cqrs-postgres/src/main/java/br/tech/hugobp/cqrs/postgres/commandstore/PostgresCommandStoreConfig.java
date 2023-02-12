package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;
import lombok.Data;

@Data
public class PostgresCommandStoreConfig {
    private String table;
    private PostgresDatabaseConfig database;
}
