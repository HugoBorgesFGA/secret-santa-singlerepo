package br.tech.hugobp.cqrs.postgres.eventstore;

import br.tech.hugobp.cqrs.postgres.utils.NonRepeatableEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EventEntity extends NonRepeatableEntity {
    private LocalDateTime createdAt;
    private String name;
    private String eventId;
    private String entityId;
    private String data;
}
