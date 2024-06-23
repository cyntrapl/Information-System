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
 * Класът FileOpen отговаря за отварянето на файлове.
 * Той разширява класа CommandClass и преписва метода execute, за да извърши операцията по отваряне на файла.
 */
public class FileOpen extends CommandClass {
    private HotelFileHandler hotelFileHandler;
    private Hotel hotel;

    /**
     * Конструира нов обект FileOpen с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public FileOpen(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
        this.hotel = Hotel.getInstance();
    }

    /**
     * Проверява дали в момента е отворен даден файл.
     * @return true, ако файлът е отворен, false в противен случай.
     */
    public boolean isFileOpen() {
        return getCurrentFile().getCurrentFileName() != null;
    }

    /**
     * Изпълнява операцията за отваряне на файла.
     * Този метод отговаря за проверката дали е отворен файл, като получава името на файла,
     * и записване на текущото съдържание на файла в него.
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
        hotel.loadAllRooms(hotelFileHandler.loadRoomsFromFile());
        System.out.println("File opened: " + fileName);
    }
}
