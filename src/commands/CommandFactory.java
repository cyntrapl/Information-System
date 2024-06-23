package commands;

import interfaces.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Класът CommandFactory създава обекти от тип Command.
 * Той съдържа методи за добавяне на команди и връщане на команда по име.
 */
public class CommandFactory {
    private Map<String, Command> commands;

    /**
     * Конструира нов обект CommandFactory.
     */
    public CommandFactory() {
        commands = new HashMap<>();
    }

    /**
     * Добавя нова команда.
     * @param commandName името на командата
     * @param command обект от тип Command
     */
    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Връща команда по име.
     * @param commandName името на командата
     * @return обект от тип Command
     */
    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}