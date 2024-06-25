package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelFileHandler;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Command to checkout a guest from a room
 */
public class Checkout extends HotelCommand {
    private int roomNumber;
    private HotelFileHandler hotelFileHandler;

    /**
     * Constructor for Checkout
     * @param scanner the scanner to read input
     */
    public Checkout(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
    }

    /**
     * Method that checks out a guest from a room
     */
    @Override
    public void execute() {
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(2, 2);
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

        roomNumber = Integer.parseInt(parts[1]);
        HotelRoom room = getHotel().findRoomByNumber(roomNumber);
        if(room == null) {
            System.out.println("Room not found!");
            return;
        }

        Set<Reservation> reservations = room.getReservations();
        if(reservations.isEmpty()){
            System.out.println("No reservations found for room " + roomNumber);
            return;
        }

        System.out.println("Select a reservation to checkout:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Reservation> reservationList = new ArrayList<>(reservations);
        for(int i = 0; i < reservationList.size(); i++){
            Reservation reservation = reservationList.get(i);
            System.out.println((i+1) + ". Reservation from " + dateFormat.format(reservation.getFromDate()) + " to " + dateFormat.format(reservation.getToDate()) + " with note: " + reservation.getNote());
        }

        int reservationIndex = getScanner().nextInt() - 1;
        if(reservationIndex < 0 || reservationIndex >= reservationList.size()){
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        Reservation reservationToRemove = reservationList.get(reservationIndex);

        // Add a prompt to confirm checkout
        System.out.println("You have selected the reservation from " + dateFormat.format(reservationToRemove.getFromDate()) + " to " + dateFormat.format(reservationToRemove.getToDate()) + " for room " + roomNumber + ". Do you want to proceed with the checkout? (yes/no)");
        String confirmation = getScanner().next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Checkout cancelled.");
            return;
        }

        room.removeReservation(reservationToRemove);
        hotelFileHandler.saveReservationsToFile();
        System.out.println("Successfully checked out reservation from " + dateFormat.format(reservationToRemove.getFromDate()) + " to " + dateFormat.format(reservationToRemove.getToDate()) + " for room " + roomNumber);
    }
}