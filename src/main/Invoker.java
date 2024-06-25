package main;

import commands.CommandFactory;
import commands.Exit;
import commands.HelpDisplay;
import commands.file_commands.FileClose;
import commands.file_commands.FileOpen;
import commands.file_commands.FileSave;
import commands.file_commands.FileSaveAs;
import commands.hotel_commands.*;
import enums.CommandName;
import hotel.HotelFileHandler;

import java.util.Scanner;

/**
 * Class to invoke commands
 */
public class Invoker {
    private HotelFileHandler hotelFileHandler;
    private Scanner scanner;
    private CommandFactory commandFactory;
    private HelpDisplay helpDisplay;


    /**
     * Constructor for Invoker
     */
    public Invoker() {
        this.scanner = new Scanner(System.in);
        this.commandFactory = new CommandFactory();
        this.helpDisplay = new HelpDisplay(scanner);
        this.hotelFileHandler = new HotelFileHandler();
        //initialize hotel file handler
        hotelFileHandler.loadRoomsBaseFile();

        //add commands
        commandFactory.addCommand(CommandName.OPEN, new FileOpen(scanner));
        commandFactory.addCommand(CommandName.CLOSE, new FileClose(scanner));
        commandFactory.addCommand(CommandName.SAVE, new FileSave(scanner));
        commandFactory.addCommand(CommandName.SAVEAS, new FileSaveAs(scanner));
        commandFactory.addCommand(CommandName.CHECKIN, new CheckIn(scanner));
        commandFactory.addCommand(CommandName.CHECKOUT, new Checkout(scanner));
        commandFactory.addCommand(CommandName.AVAILABILITY, new Availability(scanner));
        commandFactory.addCommand(CommandName.REPORT, new Report(scanner));
        commandFactory.addCommand(CommandName.UNAVAILABLE, new Unavailable(scanner));
        commandFactory.addCommand(CommandName.FIND, new Find(scanner));
        commandFactory.addCommand(CommandName.FINDSWAP, new FindImportant(scanner));
        commandFactory.addCommand(CommandName.ACTIVITIES, new ActivityList(scanner));
        commandFactory.addCommand(CommandName.HELP, helpDisplay);
        commandFactory.addCommand(CommandName.EXIT, new Exit(scanner));
    }

    /**
     * Invokes the commands
     */
    public void invoke() {
        //display help at start
        helpDisplay.displayHelp();
        //start command loop
        while (true) {
            System.out.print("> ");
            if (scanner.hasNext()) {
                String commandString = scanner.next().toLowerCase();
                if (commandFactory.getCommand(commandString) != null) {
                    commandFactory.getCommand(commandString).execute();
                } else {
                    System.out.println("Invalid command. Type 'help' to see available commands.");
                }
            }
        }
    }
}