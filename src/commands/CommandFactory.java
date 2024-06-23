package commands;

import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<String, Command> commands;

    public CommandFactory() {
        commands = new HashMap<>();
    }

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}