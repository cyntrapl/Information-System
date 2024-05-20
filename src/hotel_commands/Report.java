package hotel_commands;

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
import java.util.Scanner;

public class Report implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private int roomNumber;
    private HotelRoomFileHandler hotelRoomFileHandler;
    private Date fromDate;
    private Date toDate;

    public Report(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");
        if(parts.length < 4){
            String fromDateString = parts[1];
            String toDateString = parts[2];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fromDate = dateFormat.parse(fromDateString);
                toDate = dateFormat.parse(toDateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
            }



            for(HotelRoom hotelRoom : hotel.getRooms()){
                if (toDate.before(hotelRoom.getBooking().getFromDate()) || fromDate.after(hotelRoom.getBooking().getToDate())) {
                    continue;
                }

                Date overlapStart = fromDate.before(hotelRoom.getBooking().getFromDate()) ? hotelRoom.getBooking().getFromDate() : fromDate;
                Date overlapEnd = toDate.after(hotelRoom.getBooking().getToDate()) ? hotelRoom.getBooking().getToDate() : toDate;
                System.out.println("Room " + hotelRoom.getRoomNumber() + " checked in for " + ((overlapEnd.getTime() - overlapStart.getTime()) / (1000 * 60 * 60 * 24) + 1) + " days");
            }

        }else System.out.println("Invalid input!");
    }
}
