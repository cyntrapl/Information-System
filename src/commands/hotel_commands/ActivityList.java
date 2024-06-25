package commands.hotel_commands;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;
import hotel.Reservation;

import java.util.Scanner;

/**
 * class ActivityList extends HotelCommand
 * Command that lists all rooms that have a specific activity
 */
public class ActivityList extends HotelCommand {

    /**
     * Constructor for ActivityList
     * @param scanner Scanner object used to read user input
     */
    public ActivityList(Scanner scanner){
        super(scanner);
    }

    /**
     * Method that lists all rooms that have a specific activity
     */
    @Override
    public void execute() {
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(1, 1);
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

        Activities chosenActivity;
        Activities[] allActivities = Activities.values();

        while (true) {
            System.out.println("Activities: ");
            for (int i = 0; i < allActivities.length; i++) {
                System.out.println((i + 1) + ". " + allActivities[i].name().replace("_", " ").toLowerCase());
            }

            System.out.print("Select an activity: ");
            int choice = getScanner().nextInt();

            if (choice > 0 && choice <= allActivities.length) {
                chosenActivity = allActivities[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            break;
        }

        boolean hasRooms = false;
        for(HotelRoom room : getHotel().getRooms()){
            for (Reservation reservation : room.getReservations()) {
                if(reservation.getActivities() != null){
                    for(Activities activity : reservation.getActivities()){
                        if(activity.equals(chosenActivity)){
                            System.out.println("Room " + room.getRoomNumber() + " has activity " + chosenActivity.name().replace("_", " ").toLowerCase() + " in reservation from " + reservation.getFromDate() + " to " + reservation.getToDate());
                            hasRooms = true;
                        }
                    }
                }
            }
        }

        if(!hasRooms){System.out.println("No rooms found");}
    }
}