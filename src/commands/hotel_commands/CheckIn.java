package commands.hotel_commands;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidDateRangeException;
import hotel.HotelFileHandler;
import hotel.HotelRoom;
import hotel.Reservation;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Класът Checkin отговаря за настаняването на гостите в стаите.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за настаняване.
 */
public class CheckIn extends HotelCommand {
    private Scanner fileScanner;
    private int roomNumber;
    private Date fromDate;
    private Date toDate;
    private String note;
    private int guests;
    private HotelFileHandler hotelFileHandler;
    private List<Activities> activities;

    /**
     * Конструира нов обект CheckIn с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public CheckIn(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
    }

    /**
     * Изпълнява операцията за настаняване с опция за маркиране на стаята като недостъпна.
     * Този метод е отговорен за проверка дали е отворен файл, получаване на избраната от потребителя стая и данни за госта,
     * и настаняване на госта в избраната стая. Ако параметърът isUnavailableCommand е true, стаята се маркира като недостъпна.
     * @param isUnavailableCommand булева стойност, показваща дали стаята трябва да бъде маркирана като недостъпна.
     */
    public void execute(boolean isUnavailableCommand) {

        //check if number of arguments is valid
        String[] parts;
        try {
            if(!isUnavailableCommand) parts = checkValidNumberOfArguments(5, 6);
            else parts = checkValidNumberOfArguments(5, 5);
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

        //get room number
        roomNumber = Integer.parseInt(parts[1]);
        if(getHotel().findRoomByNumber(roomNumber) != null) {System.out.println("Room already checked in!");};

        File file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if(roomNumber > getHotel().getNumberOfRooms()){System.out.println("Invalid room number!"); return;};

        //get beds
        int beds = 0;
        while(fileScanner.hasNextLine()) {
            if(Integer.parseInt(fileScanner.next()) == roomNumber) {
                beds = Integer.parseInt(fileScanner.next());
            }else{
                fileScanner.nextLine();
            }
        }

        //get dates
        try {
            Date[] dates = parseAndValidateDatesFromParts(parts[2], parts[3]);
            fromDate = dates[0];
            toDate = dates[1];
        } catch (ParseException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //get note
        note = parts[4];
        //get guests and activities
        if (!isUnavailableCommand) {
            if(parts.length > 5){
                guests = Integer.parseInt(getScanner().next());
            }else{
                guests = beds;
            }

            activities = new ArrayList<>();
            System.out.println("Activites: ");

            Activities[] allActivities = Activities.values();

            while (true) {
                System.out.println("Activities: ");
                for (int i = 0; i < allActivities.length; i++) {
                    System.out.println((i + 1) + ". " + allActivities[i].name().replace("_", " ").toLowerCase());
                }
                System.out.println((allActivities.length + 1) + ". none");

                System.out.print("Select an activity (or 'none' to finish): ");
                int choice = getScanner().nextInt();

                if (choice == allActivities.length + 1) {
                    break;
                } else if (choice > 0 && choice <= allActivities.length) {
                    activities.add(allActivities[choice - 1]);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Selected activities: " + activities);
        }
        //create reservation and add room
         HotelRoom room = new HotelRoom(
            roomNumber,
            beds,
            new Reservation(activities, note, guests, fromDate, toDate));
        getHotel().addRoom(room);
        if(isUnavailableCommand) room.setAvailable(false);
        hotelFileHandler.saveRoomsToFile();
        if(!isUnavailableCommand) System.out.println("Successfully checked in " + roomNumber + " from " + fromDate + " to " + toDate);
        else System.out.println("Room " + roomNumber + " is now unavailable for the period from " + fromDate + " to " + toDate + " for reason: " + note);
    }

    /**
     * Изпълнява операцията за регистрация.
     * Този метод е отговорен за проверка дали е отворен файл, получаване на избраната от потребителя стая и данни за госта,
     * и настаняване на госта в избраната стая. Стаята не се маркира като недостъпна.
     */
    @Override
    public void execute(){
        execute(false);
    }
}

