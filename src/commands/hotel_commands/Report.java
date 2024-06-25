package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * class Report extends HotelCommand
 * Command that finds a room with the most beds available for a specific date
 */
public class Report extends HotelCommand {
    private Date fromDate;
    private Date toDate;

    /**
     * Constructor for Report
     * @param scanner Scanner object used to read user input
     */
    public Report(Scanner scanner) {
        super(scanner);
    }

    /**
     * Method that finds a room with the most beds available for a specific date
     */
    @Override
    public void execute() {
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(3,3);
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try {
            checkIfFileIsOpen();
        } catch (FileNotOpenException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        String fromDateString = parts[1];
        String toDateString = parts[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = dateFormat.parse(fromDateString);
            toDate = dateFormat.parse(toDateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
            return;
        }

        for(HotelRoom hotelRoom : getHotel().getRooms()){
            for (Reservation reservation : hotelRoom.getReservations()) {
                Date overlapStart = fromDate.before(reservation.getFromDate()) ? reservation.getFromDate() : fromDate;
                Date overlapEnd = toDate.after(reservation.getToDate()) ? reservation.getToDate() : toDate;
                if (overlapStart.before(overlapEnd)) {
                    if(reservation.isAvailable())
                        System.out.println("Room " + hotelRoom.getRoomNumber() + " has a reservation from " + dateFormat.format(reservation.getFromDate()) + " to " + dateFormat.format(reservation.getToDate()) + " with note: " + reservation.getNote());
                    else System.out.println("Room " + hotelRoom.getRoomNumber() + " is unavailable from " + dateFormat.format(reservation.getFromDate()) + " to " + dateFormat.format(reservation.getToDate()) + " with note: " + reservation.getNote());
                }
            }
        }
    }
}
