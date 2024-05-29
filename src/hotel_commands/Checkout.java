package hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.CurrentFile;
import singletons.Hotel;

import java.util.Scanner;

/**
 * checks out a room if it's checked in
 */
public class Checkout implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private int roomNumber;
    private HotelRoomFileHandler hotelRoomFileHandler;
    private CurrentFile currentFile;

    public Checkout(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.hotelRoomFileHandler = new HotelRoomFileHandler();
        this.scanner = scanner;
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws InvalidNumberOfArgumentsException, FileNotOpenException {

        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }

        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");

        if (parts.length != 2) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments for checkout command.");
        }


        roomNumber = Integer.parseInt(parts[1]);

        if(roomNumber > hotel.getNumberOfRooms()){System.out.println("Invalid room number!"); return;};

        if(hotel.findRoomByNumber(roomNumber) != null){
            hotel.removeRoomByRoomNumber(roomNumber);
            hotelRoomFileHandler.saveRoomsToFile();
            System.out.println("Successfully checked out " + roomNumber);
        }else{
            System.out.println("Room not found!");
        }
    }
}
