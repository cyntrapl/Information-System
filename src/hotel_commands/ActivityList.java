package hotel_commands;

import exceptions.FileNotOpenException;
import hotel.HotelRoom;
import interfaces.Command;
import enums.Activities;
import singletons.CurrentFile;
import singletons.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * lists all rooms with a certain activity chosen by the user
 */
public class ActivityList implements Command {
    private Hotel hotel;
    private Scanner scanner;

    private CurrentFile currentFile;

    public ActivityList(Scanner scanner){
        this.hotel = Hotel.getInstance();
        this.currentFile = CurrentFile.getInstance();
        this.scanner = scanner;
    }

    public void execute() throws FileNotOpenException {

        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }

        Activities chosenActivity;
        Activities[] allActivities = Activities.values();


        while (true) {
            System.out.println("Activities: ");
            for (int i = 0; i < allActivities.length; i++) {
                System.out.println((i + 1) + ". " + allActivities[i].name().replace("_", " ").toLowerCase());
            }

            System.out.print("Select an activity: ");
            int choice = scanner.nextInt();

            if (choice > 0 && choice <= allActivities.length) {
               chosenActivity = allActivities[choice - 1];
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            break;
        }

        boolean hasRooms = false;
        for(HotelRoom room : hotel.getRooms()){
            if(room.getActivities() != null){
                for(Activities activity : room.getActivities()){
                    if(activity.equals(chosenActivity)){
                        System.out.println("Room " + room.getRoomNumber() + " has activity " + chosenActivity.name().replace("_", " ").toLowerCase());
                        hasRooms = true;
                    }
                }
            }
        }

        if(!hasRooms){System.out.println("No rooms found");}
    }
}
