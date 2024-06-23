package commands.hotel_commands;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;

import java.util.Scanner;

/**
 * Класът ActivityList отговаря за извеждането на всички стаи с определена дейност, избрана от потребителя.
 * Той разширява HotelCommand и пренаписва метода execute, за да извърши операцията по изброяване на дейностите.
 */
public class ActivityList  extends HotelCommand {

    /**
     * Конструира нов обект ActivityList с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public ActivityList(Scanner scanner){
        super(scanner);
    }

    /**
     * Изпълнява операцията за изготвяне на списък с дейности.
     * Този метод отговаря за проверката дали е отворен файл, като получава избраната от потребителя дейност,
     * и изброяване на всички стаи, които имат избраната дейност.
     */
    @Override
    public void execute() {

        //check if number of arguments is valid
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(1, 1);
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //check if file is open
        try {
            checkIfFileIsOpen();
        } catch (FileNotOpenException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //get activity
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

        //check if rooms have activity
        boolean hasRooms = false;
        for(HotelRoom room : getHotel().getRooms()){
            if(room.getReservation().getActivities() != null){
                for(Activities activity : room.getReservation().getActivities()){
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
