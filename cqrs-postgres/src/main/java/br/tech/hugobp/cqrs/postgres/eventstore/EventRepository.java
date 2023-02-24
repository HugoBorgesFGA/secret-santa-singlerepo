package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;

public class EventRepository {
    private static final Function<String, String> INSERT_EVENT_INTO = (table) -> "INSERT INTO " + table + " " +
            "(id, name, created_at, hash, id_entity, data) " +
            "VALUES(?, ?, ?, ?, ?, ?::jsonb)";
    private static final Function<String, String> GET_EVENT_STREAM_PER_ENTITY_ID = (table) -> "SELECT e.id, e.name, e.created_at, e.hash, e.id_entity, e.data " +
            "FROM " + table + " e " +
            "WHERE e.id_entity = ? " +
            "ORDER BY created_at ASC";

    private static final Short DEFAULT_POSTGRES_PORT = 5432;

    private final String schema;
    private final String eventTable;
    private final Connection connection;

    public EventRepository(String schema, String commandTable, PostgresDatabaseConfig config) {
        this.schema = schema;
        this.eventTable = commandTable;
        this.connection = buildConnection(config);
    }

    public void save(EventEntity eventEntity) {
        try {
            final String insertQuery = INSERT_EVENT_INTO.apply(eventTable);
            final PreparedStatement statement = connection.prepareStatement(insertQuery);

            statement.setString(1, eventEntity.getEventId());
            statement.setString(2, eventEntity.getName());
            statement.setTimestamp(3, Timestamp.from(eventEntity.getCreatedAt().toInstant(ZoneOffset.UTC)));
            statement.setString(4, eventEntity.getNonRepeatableId());
            statement.setString(5, eventEntity.getEntityId());
            statement.setString(6, eventEntity.getData());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EventEntity> findAll(String entityId) {
        try {
            final List<EventEntity> result = new ArrayList<>();

            final String getByEntityIdQuery = GET_EVENT_STREAM_PER_ENTITY_ID.apply(eventTable);
            final PreparedStatement statement = connection.prepareStatement(getByEntityIdQuery);

            statement.setString(1, entityId);

            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final EventEntity eventEntity = EventEntity.builder()
                    .eventId(resultSet.getString(1))
                    .name(resultSet.getString(2))
                    .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                    .entityId(resultSet.getString(5))
                    .data(resultSet.getString(6))
                    .build();

                eventEntity.setNonRepeatableId(resultSet.getString(5));

                result.add(eventEntity);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection buildConnection(PostgresDatabaseConfig databaseConfig) {
        try {
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

            final Properties props = new Properties();
            props.setProperty("user", databaseConfig.getUsername());
            props.setProperty("password", databaseConfig.getPassword());
            props.setProperty("ssl", "false");

            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            throw new RuntimeException("Could not establish connection to database. Review config!", e);
        }
    }
}
