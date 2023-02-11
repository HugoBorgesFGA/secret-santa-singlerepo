package br.tech.hugobp.cqrs.command;

import br.tech.hugobp.cqrs.event.EventPublisher;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class CommandExecutor {
    private final CommandHandlerRegistry handlerRegistry;
    private final CommandStore commandStore;
    private final EventPublisher eventPublisher;

    public String execute(Command command) {
        RuntimeException error = null;
        try {
            final CommandHandler<? extends Command> handler = handlerRegistry.getHandler(command);
            handler.handle(command, eventPublisher);
        } catch (RuntimeException runtimeException) {
            error = runtimeException;
        } finally {
            if (Objects.nonNull(error)) {
                commandStore.storeFailedCommand(command, error);
                throw error;
            }
        }

        commandStore.storeSuccessfulCommand(command);
        return command.getId();
    }
}
