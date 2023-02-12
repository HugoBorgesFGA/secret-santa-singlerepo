package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public class CommandRepository {

    private static final Function<String, String> INSERT_COMMAND_INTO = (table) -> "INSERT INTO " + table + " " +
            "(id, name, created_at, hash, id_entity, data, error) " +
            "VALUES(?, ?, to_timestamp(?), ?, ?, ?::jsonb, ?)";
    private static final Short DEFAULT_POSTGRES_PORT = 5432;

    private final String schema;
    private final String commandTable;
    private final Connection connection;

    public CommandRepository(String schema, String commandTable, PostgresDatabaseConfig config) {
        this.schema = schema;
        this.commandTable = commandTable;
        this.connection = buildConnection(config);
    }

    public void save(CommandEntity commandEntity) {
        try {
            final String insertQuery = INSERT_COMMAND_INTO.apply(commandTable);
            final PreparedStatement statement = connection.prepareStatement(insertQuery);
            //statement.setString(1, commandEntity.getNonRepeatableId());
            //statement.setNString(1, commandTable);
            statement.setString(1, commandEntity.getCommandId());
            statement.setString(2, commandEntity.getName());
            statement.setLong(3, commandEntity.getCreatedAt());
            statement.setString(4, commandEntity.getNonRepeatableId());
            statement.setString(5, commandEntity.getEntityId());
            statement.setString(6, commandEntity.getData());
            statement.setString(7, commandEntity.getError());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection buildConnection(PostgresDatabaseConfig databaseConfig) {
        try {
            final String protocol = "jdbc:postgresql";
            final short port = Optional.ofNullable(databaseConfig.getPort())
                .orElse(DEFAULT_POSTGRES_PORT);

            final String schema = Optional.ofNullable(this.schema)
                .orElse("public");

            final String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s?currentSchema=%s",
                databaseConfig.getHost(),
                port,
                databaseConfig.getName(),
                schema
            );

            Properties props = new Properties();
            props.setProperty("user", databaseConfig.getUsername());
            props.setProperty("password", databaseConfig.getPassword());
            props.setProperty("ssl", "false");

            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection to database. Review config!", e);
        }
    }
}
