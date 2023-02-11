package br.tech.hugobp.cqrs.command;


import br.tech.hugobp.cqrs.command.exceptions.UnknownCommandException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandHandlerRegistry {

    private final Map<String, CommandHandler<? extends Command>> commandHandlersByCommandId;

    public CommandHandlerRegistry(Set<CommandHandler<? extends Command>> commandHandlers) {
        this.commandHandlersByCommandId = commandHandlers.stream()
            .collect(Collectors.toMap(CommandHandler::getTargetCommandId, Function.identity()));
    }

    public CommandHandler<? extends Command> getHandler(Command cmd) {
        return Optional.ofNullable(commandHandlersByCommandId.get(cmd.getName()))
            .orElseThrow(() -> new UnknownCommandException(cmd));
    }
}
