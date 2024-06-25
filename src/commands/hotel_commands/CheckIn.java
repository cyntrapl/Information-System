package commands.hotel_commands;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidDateRangeException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelFileHandler;
import hotel.HotelRoom;
import hotel.Reservation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static hotel.HotelFileHandler.dateFormat;

/**
 * class CheckIn extends HotelCommand
 * Command that checks in a guest in a room
 */
public class CheckIn extends HotelCommand {
    private int roomNumber;
    private Date fromDate;
    private Date toDate;
    private String note;
    private int guests;
    private HotelFileHandler hotelFileHandler;
    private List<Activities> activities;

    /**
     * Constructor for CheckIn
     * @param scanner Scanner object used to read user input
     */
    public CheckIn(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
    }

    /**
     * Method that checks in a guest in a room
     * @param isUnavailableCommand boolean that indicates if the command is for checking in or making a room unavailable
     */
    public void execute(boolean isUnavailableCommand) {
        String[] parts;
        try {
            if(!isUnavailableCommand) parts = checkValidNumberOfArguments(5, 6);
            else parts = checkValidNumberOfArguments(5, 5);
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

        try {
            Date[] dates = parseAndValidateDatesFromParts(parts[2], parts[3]);
            fromDate = dates[0];
            toDate = dates[1];
        } catch (ParseException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // check for overlapping reservations
        for (Reservation existingReservation : room.getReservations()) {
            if (!(existingReservation.getToDate().before(fromDate) || existingReservation.getFromDate().after(toDate))) {
                System.out.println("Error: There is an overlapping reservation for this room and date range.");
                return;
            }
        }

        note = parts[4];
        if (!isUnavailableCommand) {
            if(parts.length > 5){
                guests = Integer.parseInt(getScanner().next());
            }else{
                guests = room.getBeds();
            }

            activities = new ArrayList<>();
            System.out.println("Activities: ");

            Activities[] allActivities = Activities.values();

            while (true) {
                System.out.println("Activities: ");
                for (int i = 0; i < allActivities.length; i++) {
                    System.out.println((i + 1) + ". " + allActivities[i].name().replace("_", " ").toLowerCase());
                }
                System.out.println((allActivities.length + 1) + ". none");
                System.out.println("Selected activities: " + activities.stream().map(a -> a.name().replace("_", " ").toLowerCase()).collect(Collectors.joining(", ")));
                System.out.print("Select an activity (or 'none' to finish): ");
                int choice = getScanner().nextInt();

                if (choice == allActivities.length + 1) {
                    break;
                } else if (choice > 0 && choice <= allActivities.length) {
                    Activities selectedActivity = allActivities[choice - 1];
                    if (activities.contains(selectedActivity)) {
                        activities.remove(selectedActivity);
                        System.out.println("Removed activity: " + selectedActivity.name().replace("_", " ").toLowerCase());
                        continue;
                    }
                    activities.add(selectedActivity);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Selected activities: " + activities);
        }
        Reservation reservation = new Reservation(activities, note, guests, fromDate, toDate);

        // Add a prompt to confirm checkout
        if(!isUnavailableCommand) System.out.println("Reservation from " + dateFormat.format(reservation.getFromDate()) + " to " + dateFormat.format(reservation.getToDate()) + " for room " + roomNumber + " with Note: " + note + ". Do you want to proceed with the command? (yes/no)");
        else System.out.println("Unavailable from " + dateFormat.format(reservation.getFromDate()) + " to " + dateFormat.format(reservation.getToDate()) + " for room " + roomNumber + " with Note: " + note + ". Do you want to proceed with the command? (yes/no)");
        String confirmation = getScanner().next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            if(!isUnavailableCommand) System.out.println("Checkin cancelled.");
            else System.out.println("Unavailable cancelled.");
            return;
        }

        room.addReservation(reservation);
        if(isUnavailableCommand) reservation.setAvailable(false);
        hotelFileHandler.saveReservationsToFile();
        if(!isUnavailableCommand) System.out.println("Successfully checked in " + roomNumber + " from " + fromDate + " to " + toDate);
        else System.out.println("Room " + roomNumber + " is now unavailable for the period from " + fromDate + " to " + toDate + " for reason: " + note);
    }

    /**
     * Method that checks in a guest in a room
     */
    @Override
    public void execute(){
        execute(false);
    }
}

