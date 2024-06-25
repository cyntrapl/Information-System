package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidDateRangeException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * class Find extends HotelCommand
 * Command that finds a room with the most beds available for a specific date
 */
public class Find extends HotelCommand {
    private Date fromDate;
    private Date toDate;
    private int roomNumber;
    private int beds;
    private int currentFreeRoomNumber = 0;
    private int currentFreeBeds = 0;

    /**
     * Constructor for Find
     * @param scanner Scanner object used to read user input
     */
    public Find(Scanner scanner) {
        super(scanner);
    }

    /**
     * Method that finds a room with the most beds available for a specific date
     */
    @Override
    public void execute() {
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(4, 4);
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

        currentFreeBeds = Integer.MAX_VALUE;
        currentFreeRoomNumber = 0;

        int bedsInput = Integer.parseInt(parts[1]);

        try {
            Date[] dates = parseAndValidateDatesFromParts(parts[2], parts[3]);
            fromDate = dates[0];
            toDate = dates[1];
        } catch (ParseException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        for(HotelRoom hotelRoom : getHotel().getRooms()){
            if(hotelRoom.getBeds() < bedsInput || currentFreeBeds < hotelRoom.getBeds()) continue;
            boolean isAvailableAndRightDates = true;
            for (Reservation reservation : hotelRoom.getReservations()) {
                if(!(reservation.getFromDate().after(toDate) || reservation.getToDate().before(fromDate))){
                    isAvailableAndRightDates = false;
                    break;
                }
            }
            if(isAvailableAndRightDates){
                if (currentFreeRoomNumber == 0) {
                    currentFreeRoomNumber = hotelRoom.getRoomNumber();
                    currentFreeBeds = hotelRoom.getBeds();
                }
                else if(currentFreeBeds > hotelRoom.getBeds()) {
                    currentFreeRoomNumber = hotelRoom.getRoomNumber();
                    currentFreeBeds = hotelRoom.getBeds();
                }
            }
        }
        if(currentFreeRoomNumber == 0){
            System.out.println("No room found!");
        }else System.out.println("Room " + currentFreeRoomNumber + " has been found with " + currentFreeBeds + " beds!");
    }
}