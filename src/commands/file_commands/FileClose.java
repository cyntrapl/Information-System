package commands.file_commands;

import commands.CommandClass;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import singletons.Hotel;

import java.util.Scanner;

/**
 * Това е класът FileOpen.
 * Той отговаря за отварянето на файлове.
 */
public class FileClose extends CommandClass {
    private Hotel hotel;

    /**
     * Това е конструкторът на класа FileOpen.
     * @param scanner Скенерът, използван за въвеждане от потребителя.
     */
    public FileClose(Scanner scanner) {
        super(scanner);
        this.hotel = Hotel.getInstance();
    }

    /**
     * Този метод проверява дали даден файл е отворен.
     * @return true, ако файлът е отворен, false в противен случай.
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
