package hotel_commands;

import enums.Activities;
import hotel.Booking;
import hotel.HotelRoom;
import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.Hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Unavailable implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private Scanner fileScanner;
    private int roomNumber;
    private Date fromDate;
    private Date toDate;
    private String note;
    private int guests;
    private HotelRoomFileHandler hotelRoomFileHandler;

    public Unavailable(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.hotelRoomFileHandler = new HotelRoomFileHandler();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");

        roomNumber = Integer.parseInt(parts[1]);



        File file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(roomNumber > hotel.getNumberOfRooms()){System.out.println("Invalid room number!"); return;};

        int beds = 0;
        while(fileScanner.hasNextLine()) {
            if(Integer.parseInt(fileScanner.next()) == roomNumber) {
                beds = Integer.parseInt(fileScanner.next());
            }else{
                fileScanner.nextLine();
            }
        }

        String fromDateString = parts[2];
        String toDateString = parts[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = dateFormat.parse(fromDateString);
            toDate = dateFormat.parse(toDateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
            return;
        }
        Booking booking = new Booking(fromDate, toDate);
        note = parts[4];
        guests = 0;

        List<Activities> activities = new ArrayList<>();

        if(hotel.findRoomByNumber(roomNumber) == null){
            HotelRoom room = new HotelRoom(
                    roomNumber,
                    beds,
                    note,
                    booking,
                    guests,
                    activities);
            room.setAvailable(false);
            hotel.addRoom(room);
            hotelRoomFileHandler.saveRoomsToFile();
            System.out.println("Room " + roomNumber + " unavailable from " + fromDate + " to " + toDate);
        }else{
            System.out.println("Room already checked in!");
        }

        //Bonus: Activity logic do later cuz bs
        //List<List<String>> activities = new ArrayList<>();
        //System.out.println("Activites: ");
    }
}
