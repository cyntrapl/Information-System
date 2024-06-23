package commands;

import exceptions.InvalidNumberOfArgumentsException;

import java.util.Scanner;

public class Exit extends CommandClass{

    public Exit(Scanner scanner) {
        super(scanner);
    }

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