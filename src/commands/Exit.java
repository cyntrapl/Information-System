package commands;

import exceptions.InvalidNumberOfArgumentsException;

import java.util.Scanner;

/**
 * class Exit extends CommandClass and overrides the execute method
 * Command that exits the console
 */
public class Exit extends CommandClass{

    /**
     * Constructor for Exit
     * @param scanner Scanner object used to read input
     */
    public Exit(Scanner scanner) {
        super(scanner);
    }

    /**
     * Method that exits the console
     */
    @Override
    public void execute() {
        try {
            checkValidNumberOfArguments(1, 1);
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Exiting the console...");
        System.exit(0);
    }
}