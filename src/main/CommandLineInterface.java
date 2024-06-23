package main;

import commands.CommandFactory;
import commands.Exit;
import commands.HelpDisplay;
import commands.file_commands.*;
import commands.hotel_commands.*;

import java.util.Scanner;

/**
 * Класът CommandLineInterface съдържа метода main, който стартира командния интерфейс на програмата.
 */
public class CommandLineInterface {
    public static void main(String[] args) {
        //initialize scanner and command factory
        Scanner scanner = new Scanner(System.in);
        String command;
        CommandFactory commandFactory = new CommandFactory();
        HelpDisplay helpDisplay = new HelpDisplay(scanner);

        //add commands
        commandFactory.addCommand("open", new FileOpen(scanner));
        commandFactory.addCommand("close", new FileClose(scanner));
        commandFactory.addCommand("save", new FileSave(scanner));
        commandFactory.addCommand("saveas", new FileSaveAs(scanner));
        commandFactory.addCommand("checkin", new CheckIn(scanner));
        commandFactory.addCommand("checkout", new Checkout(scanner));
        commandFactory.addCommand("availability", new Availability(scanner));
        commandFactory.addCommand("report", new Report(scanner));
        commandFactory.addCommand("unavailable", new Unavailable(scanner));
        commandFactory.addCommand("find", new Find(scanner));
        commandFactory.addCommand("find!", new FindImportant(scanner));
        commandFactory.addCommand("activities", new ActivityList(scanner));
        commandFactory.addCommand("help", helpDisplay);
        commandFactory.addCommand("exit", new Exit(scanner));



        //display help at start
        helpDisplay.displayHelp();
        //start command loop
        while (true) {
            System.out.print("> ");
            if (scanner.hasNext()) {
                command = scanner.next().toLowerCase();
                if (commandFactory.getCommand(command) != null) {
                    commandFactory.getCommand(command).execute();
                } else {
                    System.out.println("Invalid command. Type 'help' to see available commands.");
                }
            }
        }
    }
}