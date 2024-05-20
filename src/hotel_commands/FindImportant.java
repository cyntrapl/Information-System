package hotel_commands;

import hotel.Booking;
import hotel.HotelRoom;
import interfaces.Command;
import singletons.Hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * same logic as find except if there isnt a free room it will try and figure out if it can swap rooms to free up space
 */
public class FindImportant implements Command {
    private Hotel hotel;
    private Scanner scanner;
    private int roomNumber;
    private int beds;
    private int currentFreeRoomNumber = 0;
    private int currentFreeBeds = 0;
    private Date fromDate;
    private Date toDate;
    private Scanner fileScanner;
    private File file;
    private List<Integer> potentialRooms = new ArrayList<>();
    private int bedsInput;

    public FindImportant(Scanner scanner) {
        this.hotel = Hotel.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");
        if(parts.length > 4) {
            System.out.println("Invalid number of arguments!");
        }else{
            bedsInput = Integer.parseInt(parts[1]);
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

            fileScanner = null;
            file = new File("rooms.txt");
            try {
                fileScanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while(fileScanner.hasNextLine()) {
                roomNumber =  Integer.parseInt(fileScanner.next());
                beds = Integer.parseInt(fileScanner.next());
                if(beds > bedsInput) potentialRooms.add(roomNumber);
                if(beds != bedsInput) continue;;
                if(hotel.findRoomByNumber(roomNumber) != null){
                    HotelRoom tempRoom = hotel.findRoomByNumber(roomNumber);
                    boolean isAvailableAndRightDates = tempRoom.isAvailable() && ( tempRoom.getBooking().getFromDate().after(toDate) || tempRoom.getBooking().getToDate().before(fromDate) ) ;
                    if(isAvailableAndRightDates){
                        if (currentFreeRoomNumber == 0) {
                            currentFreeRoomNumber = roomNumber;
                            currentFreeBeds = hotel.findRoomByNumber(roomNumber).getBeds();
                        }
                        else if(currentFreeBeds > hotel.findRoomByNumber(roomNumber).getBeds()) {
                            currentFreeRoomNumber = roomNumber;
                            currentFreeBeds = hotel.findRoomByNumber(roomNumber).getBeds();
                        }
                    }
                }else{
                    if (currentFreeRoomNumber == 0) {
                        currentFreeRoomNumber = roomNumber;
                        currentFreeBeds = beds;
                    }
                    else if(currentFreeBeds > beds) {
                        currentFreeRoomNumber = roomNumber;
                        currentFreeBeds = beds;
                    }
                }
            }
        }
        if(currentFreeRoomNumber == 0){
            List<HotelRoom> potentialSwaps = new ArrayList<>();
            for(HotelRoom room : hotel.getRooms()){
                if(room.getBeds() == bedsInput && potentialSwaps.size() < 3) potentialSwaps.add(room);
            }

            if(!potentialSwaps.isEmpty() && !potentialRooms.isEmpty()){
                for(HotelRoom room : potentialSwaps){
                    for(int roomNumber : potentialRooms){
                        System.out.println("Room " + room.getRoomNumber() + " can be swapped with " + roomNumber);
                    }
                }
            }else{
                System.out.println("No room found!");
            }
        }else System.out.println("Room " + currentFreeRoomNumber + " has been found with " + currentFreeBeds + " beds!");
    }
}

