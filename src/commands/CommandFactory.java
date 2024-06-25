package commands;

import enums.CommandName;
import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * class CommandFactory
 * Factory class that creates commands
 */
public class CommandFactory {
    private Map<CommandName, Command> commands;

    /**
     * Constructor for CommandFactory
     */
    public CommandFactory() {
        commands = new HashMap<>();
    }

    /**
     * Method that adds a command to the factory
     * @param commandName CommandName object
     * @param command Command object
     */
    public void addCommand(CommandName commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Method that returns a command based on a string
     * @param commandString String object
     * @return Command object
     */
    public Command getCommand(String commandString) {
        try {
            CommandName commandName = CommandName.fromTextValue(commandString);
            return commands.get(commandName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}