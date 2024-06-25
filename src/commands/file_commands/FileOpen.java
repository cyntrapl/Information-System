package commands.file_commands;

import commands.CommandClass;
import exceptions.InvalidFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelFileHandler;
import singletons.Hotel;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Command to open a file
 */
public class FileOpen extends CommandClass {
    private HotelFileHandler hotelFileHandler;
    private Hotel hotel;

    /**
     * Constructor for FileOpen
     * @param scanner the scanner to read input
     */
    public FileOpen(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
        this.hotel = Hotel.getInstance();
    }

    /**
     * Checks if a file is open
     * @return true if a file is open, false otherwise
     */
    public boolean isFileOpen() {
        return getCurrentFile().getCurrentFileName() != null;
    }

    /**
     * Checks if a file is open and throws an exception if it is not
     */
    @Override
    public void execute() {
        String fileName;

        //check if number of arguments is valid
        try {
            fileName = checkValidNumberOfArguments(2, 2)[1];
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //regex moment
        try {
            checkIfValidFileName(fileName);
        } catch (InvalidFileNameException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        File file = new File(fileName);

        //check if file exists and create it if it doesn't
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.out.println("Error: Unable to create the file.");
                    return;
                }
                System.out.println("File created and opened: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        Scanner fileScanner;
        try {
            fileScanner = new Scanner(file);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        getCurrentFile().getFileContent().setLength(0);
        while (fileScanner.hasNextLine())
            getCurrentFile().getFileContent().append(fileScanner.nextLine()).append("\n");
        getCurrentFile().setCurrentFileName(fileName);
        hotelFileHandler.loadReservationsFromFile();
        System.out.println("File opened: " + fileName);
    }
}
