package file_commands;

import exceptions.InvalidFileNameException;
import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.CurrentFile;
import singletons.Hotel;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * opens file
 */
public class FileOpen implements Command {
    private CurrentFile currentFile;
    private Scanner scanner;
    private HotelRoomFileHandler hotelRoomFileHandler;
    private Hotel hotel;

    public FileOpen(Scanner scanner) {
        this.currentFile = CurrentFile.getInstance();
        this.scanner = scanner;
        this.hotelRoomFileHandler = new HotelRoomFileHandler();
        this.hotel = Hotel.getInstance();
    }

    @Override
    public void execute() throws IOException, InvalidFileNameException {
        if(currentFile.getCurrentFileName() == null){
            String fileName = scanner.next();
            //Regex moment
            Pattern pattern = Pattern.compile(".*\\.txt$");
            Matcher matcher = pattern.matcher(fileName);
            if(!matcher.matches()){
                throw new InvalidFileNameException("Invalid File Name!");
            }

            File file = new File(fileName);

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.out.println("Error: Unable to create the file.");
                    return;
                }
                System.out.println("File created and opened: " + fileName);
            }

            Scanner fileScanner = new Scanner(file);

            currentFile.getFileContent().setLength(0);
            while (fileScanner.hasNextLine())
               currentFile.getFileContent().append(fileScanner.nextLine()).append("\n");
            currentFile.setCurrentFileName(fileName);
            hotel.loadAllRooms(hotelRoomFileHandler.loadRoomsFromFile());
            System.out.println("File opened: " + fileName);
        }else {
            System.out.println("a file is already open!");
            scanner.next();
        }



    }
}
