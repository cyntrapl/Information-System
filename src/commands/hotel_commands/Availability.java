package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * class Availability extends HotelCommand
 * Command that checks the availability of rooms for a specific date
 */
public class Availability extends HotelCommand {
    private ArrayList<Integer> availableRooms;
    private Date date;

    /**
     * Constructor for Availability
     * @param scanner Scanner object used to read user input
     */
    public Availability(Scanner scanner) {
        super(scanner);
        availableRooms = new ArrayList<>();
    }

    /**
     * Method that checks the availability of rooms for a specific date
     */
    @Override
    public void execute() {

        availableRooms.clear();

        String[] parts;
        try {
            parts = checkValidNumberOfArguments(1, 2);
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

        for(HotelRoom hotelRoom : getHotel().getRooms()){
            boolean isAvailable = true;
            for (Reservation reservation : hotelRoom.getReservations()) {
                if(date.after(reservation.getFromDate()) && date.before(reservation.getToDate())){
                    isAvailable = false;
                    break;
                }
            }
            if(isAvailable){
                availableRooms.add(hotelRoom.getRoomNumber());
            }
        }

        System.out.println("Available rooms: ");
        for(Integer roomNumber : availableRooms){
            System.out.println(roomNumber);
        }
    }
}
