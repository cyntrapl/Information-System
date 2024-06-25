package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidDateRangeException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * class FindImportant extends HotelCommand
 * Command that finds a room with the most beds available for a specific date
 */
public class FindImportant extends HotelCommand {
    private int roomNumber;
    private int beds;
    private int currentFreeRoomNumber = 0;
    private int currentFreeBeds = 0;
    private Date fromDate;
    private Date toDate;
    private List<HotelRoom> potentialRooms = new ArrayList<>();
    private int bedsInput;

    /**
     * Constructor for FindImportant
     * @param scanner Scanner object used to read user input
     */
    public FindImportant(Scanner scanner) {
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

        bedsInput = Integer.parseInt(parts[1]);

        try {
            Date[] dates = parseAndValidateDatesFromParts(parts[2], parts[3]);
            fromDate = dates[0];
            toDate = dates[1];
        } catch (ParseException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        currentFreeBeds = Integer.MAX_VALUE;
        currentFreeRoomNumber = 0;

        for(HotelRoom hotelRoom : getHotel().getRooms()){
            if(hotelRoom.getBeds() != bedsInput){
                continue;
            }
            Reservation reservationToSwap = null;
            for (Reservation reservation : hotelRoom.getReservations()) {
                if(!(reservation.getFromDate().after(toDate) || reservation.getToDate().before(fromDate)) && !reservation.isAvailable()){
                    reservationToSwap = reservation;
                    break;
                }
            }
            if(reservationToSwap != null){
                for(HotelRoom room : getHotel().getRooms()){
                    if(checkRoomAvailabilityForPeriod(room) != null && room.getBeds() >= bedsInput && room != hotelRoom){
                        potentialRooms.add(room);
                    }
                }
                if(!potentialRooms.isEmpty()){
                    System.out.println("Room " + hotelRoom.getRoomNumber() + " is not free, but can be swapped with:");
                    for(HotelRoom room : potentialRooms){
                        System.out.println("Room " + room.getRoomNumber());
                    }
                    return;
                }
            } else {
                System.out.println("Room " + hotelRoom.getRoomNumber() + " is free and has the exact number of beds needed.");
                return;
            }
        }

        System.out.println("No swap found!");
    }

    /**
     * Method that checks if a room is available for a specific period
     * @param hotelRoom HotelRoom object
     * @return HotelRoom object
     */
    private HotelRoom checkRoomAvailabilityForPeriod(HotelRoom hotelRoom){
        boolean isAvailableAndRightDates = true;
        for (Reservation reservation : hotelRoom.getReservations()) {
            if(!(reservation.getFromDate().after(toDate) || reservation.getToDate().before(fromDate)) ){
                isAvailableAndRightDates = false;
                break;
            }
        }
        return isAvailableAndRightDates ? hotelRoom : null;
    }
}

