package commands.file_commands;

import commands.CommandClass;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Command to save a file
 */
public class FileSave extends CommandClass {

    /**
     * Constructor for FileSave
     * @param scanner the scanner to read input
     */
    public FileSave(Scanner scanner) {
        super(scanner);
    }

    /**
     * Executes the command to save the current file
     */
    @Override
    public void execute() {
        //check if number of arguments is valid

        String fileName = getCurrentFile().getCurrentFileName();

        try {
            checkValidNumberOfArguments(1, 1);
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //check if file is open
        try {
            checkIfFileIsOpen();
        } catch (FileNotOpenException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //save file
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            writer.write(getCurrentFile().getFileContent().toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Successfully saved " + fileName);
    }
}
