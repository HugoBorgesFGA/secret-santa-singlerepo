package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.event.Event;
import br.tech.hugobp.cqrs.event.EventStore;
import br.tech.hugobp.cqrs.postgres.PostgresDatabaseConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostgresEventStore implements EventStore {

    private final String eventTable;
    private final PostgresDatabaseConfig databaseConfig;

    @Override
    public void store(Event event) {

    }
}
