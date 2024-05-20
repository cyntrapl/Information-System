package hotel_commands;

import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.Hotel;

import java.util.Scanner;

public class Checkout implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private int roomNumber;
    private HotelRoomFileHandler hotelRoomFileHandler;

    public Checkout(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.hotelRoomFileHandler = new HotelRoomFileHandler();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        roomNumber = Integer.parseInt(scanner.next());

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
