package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.command.Command;
import br.tech.hugobp.cqrs.command.exceptions.CommandProcessingException;
import br.tech.hugobp.cqrs.postgres.utils.NonRepeatableIdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandMapper {

    private final ObjectMapper objectMapper;

    public CommandEntity toEntity(Command command, String errorMessage) {
        final CommandEntity commandEntity;
        try {
            commandEntity = CommandEntity.builder()
                .createdAt(command.getCreatedAt())
                .name(command.getName())
                .commandId(command.getId())
                .entityId(command.getEntityId())
                .data(objectMapper.writeValueAsString(command.getData()))
                .error(errorMessage)
                .build();

            NonRepeatableIdGenerator.stampAsNonRepeatable(commandEntity);
            return commandEntity;
        } catch (JsonProcessingException e) {
            throw new CommandProcessingException("Could not persist command due to serialization problem", e);
        }
    }
}
