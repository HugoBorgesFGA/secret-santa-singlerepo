package br.tech.hugobp.cqrs.command;


import br.tech.hugobp.cqrs.event.EventPublisher;

public abstract class CommandHandler<Cmd extends Command> {

    private final String targetCommandId;

    public CommandHandler(Class<Cmd> cmdClass) {
        this.targetCommandId = Command.createCommandId(cmdClass);
    }

    public String getTargetCommandId() {
        return targetCommandId;
    }

    public void handle(Command command, EventPublisher eventPublisher) {
        this.process((Cmd) command, eventPublisher);
    }

    public abstract void process(Cmd command, EventPublisher eventPublisher);
}
