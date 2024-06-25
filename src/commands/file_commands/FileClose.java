package commands.file_commands;

import commands.CommandClass;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import singletons.Hotel;

import java.util.Scanner;

/**
 * Command to close the current file
 */
public class FileClose extends CommandClass {
    private Hotel hotel;

    /**
     * Constructor for FileClose
     * @param scanner the scanner to read input
     */
    public FileClose(Scanner scanner) {
        super(scanner);
        this.hotel = Hotel.getInstance();
    }

    /**
     * Executes the command to close the current file
     */
    @Override
    public void execute() {
        //check if number of arguments is valid
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

        //close file
        getCurrentFile().setCurrentFileName(null);
        getCurrentFile().getFileContent().setLength(0);
        hotel.clearAllRooms();
        System.out.println("File has been closed!");
    }
}
