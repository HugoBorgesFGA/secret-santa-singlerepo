package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.command.Command;
import br.tech.hugobp.cqrs.command.exceptions.CommandProcessingException;
import br.tech.hugobp.cqrs.postgres.utils.NonRepeatableIdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CommandMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static CommandEntity toEntity(Command command, String errorMessage) {
        final CommandEntity commandEntity;
        try {
            commandEntity = CommandEntity.builder()
                .createdAt(command.getCreatedAt())
                .name(command.getName())
                .commandId(command.getId())
                .entityId(command.getEntityId())
                .version(command.getVersion())
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
