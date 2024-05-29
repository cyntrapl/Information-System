package hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.CurrentFile;
import singletons.Hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * available rooms at a certain date
 */
public class Availability implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private ArrayList<Integer> availableRooms;
    private Date date;
    private CurrentFile currentFile;

    public Availability(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.scanner = scanner;
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws InvalidNumberOfArgumentsException, FileNotOpenException {

        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }

        this.availableRooms = new ArrayList<>();
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");

        if (parts.length > 3 || parts.length < 2) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments for checkin command.");
        }

        if(parts.length == 2){
            String dateString = parts[1];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
                return;
            }
        }else date = new Date();


        Scanner fileScanner = null;
        File file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        while(fileScanner.hasNextLine()) {
            availableRooms.add(Integer.parseInt(fileScanner.next()));
            fileScanner.nextLine();
        }

        for(HotelRoom hotelRoom : hotel.getRooms()){
            if( (date.after(hotelRoom.getBooking().getFromDate()) && date.before(hotelRoom.getBooking().getToDate()) ) || !hotelRoom.isAvailable()){
                availableRooms.remove((Integer)hotelRoom.getRoomNumber());
            }
        }

        System.out.println("Available rooms: ");
        for(Integer roomNumber : availableRooms){
            System.out.println(roomNumber);
        }
    }
}
