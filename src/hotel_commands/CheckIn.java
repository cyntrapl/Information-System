package hotel_commands;

import enums.Activities;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.Booking;
import hotel.HotelRoom;
import hotel.HotelRoomFileHandler;
import interfaces.Command;
import singletons.CurrentFile;
import singletons.Hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * checks in someone at the hotel
 */
public class CheckIn implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private Scanner fileScanner;
    private int roomNumber;
    private Date fromDate;
    private Date toDate;
    private String note;
    private int guests;
    private HotelRoomFileHandler hotelRoomFileHandler;
    private CurrentFile currentFile;

    public CheckIn(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.hotelRoomFileHandler = new HotelRoomFileHandler();
        this.scanner = scanner;
        this.currentFile = CurrentFile.getInstance();
    }

    @Override
    public void execute() throws InvalidNumberOfArgumentsException, FileNotOpenException {

        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }

        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");

        if (parts.length < 5 || parts.length > 6) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments for checkin command.");
        }

        roomNumber = Integer.parseInt(parts[1]);



        File file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(roomNumber > hotel.getNumberOfRooms()){System.out.println("Invalid room number!"); return;};

        int beds = 0;
        while(fileScanner.hasNextLine()) {
            if(Integer.parseInt(fileScanner.next()) == roomNumber) {
                beds = Integer.parseInt(fileScanner.next());
            }else{
                fileScanner.nextLine();
            }
        }

        String fromDateString = parts[2];
        String toDateString = parts[3];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = dateFormat.parse(fromDateString);
            toDate = dateFormat.parse(toDateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
            return;
        }
        Booking booking = new Booking(fromDate, toDate);
        note = parts[4];
        if(parts.length > 5){
            guests = Integer.parseInt(scanner.next());
        }else{
            guests = beds;
        }

        List<Activities> activities = new ArrayList<>();
        System.out.println("Activites: ");

        Activities[] allActivities = Activities.values();

        while (true) {
            System.out.println("Activities: ");
            for (int i = 0; i < allActivities.length; i++) {
                System.out.println((i + 1) + ". " + allActivities[i].name().replace("_", " ").toLowerCase());
            }
            System.out.println((allActivities.length + 1) + ". none");

            System.out.print("Select an activity (or 'none' to finish): ");
            int choice = scanner.nextInt();

            if (choice == allActivities.length + 1) {
                break;
            } else if (choice > 0 && choice <= allActivities.length) {
                activities.add(allActivities[choice - 1]);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Selected activities: " + activities);

        if(hotel.findRoomByNumber(roomNumber) == null){
            HotelRoom room = new HotelRoom(
                    roomNumber,
                    beds,
                    note,
                    booking,
                    guests,
                    activities);
            room.setAvailable(true);
            hotel.addRoom(room);
            hotelRoomFileHandler.saveRoomsToFile();
            System.out.println("Successfully checked in " + roomNumber + " from " + fromDate + " to " + toDate);
        }else{
            System.out.println("Room already checked in!");
        }


    }
}

