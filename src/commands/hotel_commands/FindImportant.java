package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidDateRangeException;
import hotel.HotelRoom;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Класът FindImportant отговаря за намирането на важна стая.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за намиране на важна стая.
 */
public class FindImportant extends HotelCommand {
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

    /**
     * Конструира нов обект FindImportant с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public FindImportant(Scanner scanner) {
        super(scanner);
    }

    /**
     * Изпълнява операцията за намиране на важна стая.
     * Този метод отговаря за проверката дали даден файл е отворен, като получава броя на леглата,
     * началната и крайната дата на периода, за който се търси важна стая.
     */
    @Override
    public void execute() {

        //check if number of arguments is valid
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(4, 4);
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

        bedsInput = Integer.parseInt(parts[1]);

        //get dates
        try {
            Date[] dates = parseAndValidateDatesFromParts(parts[2], parts[3]);
            fromDate = dates[0];
            toDate = dates[1];
        } catch (ParseException | InvalidDateRangeException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //open rooms.txt file
        fileScanner = null;
        file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        //find room
        while(fileScanner.hasNextLine()) {
            roomNumber =  Integer.parseInt(fileScanner.next());
            beds = Integer.parseInt(fileScanner.next());
            if(beds < bedsInput) potentialRooms.add(roomNumber);
            if(beds != bedsInput) continue;;
            if(getHotel().findRoomByNumber(roomNumber) != null){
                HotelRoom tempRoom = getHotel().findRoomByNumber(roomNumber);
                boolean isAvailableAndRightDates = tempRoom.isAvailable() && ( tempRoom.getReservation().getFromDate().after(toDate) || tempRoom.getReservation().getToDate().before(fromDate) ) ;
                if(isAvailableAndRightDates){
                    if (currentFreeRoomNumber == 0) {
                        currentFreeRoomNumber = roomNumber;
                        currentFreeBeds = getHotel().findRoomByNumber(roomNumber).getBeds();
                    }
                    else if(currentFreeBeds > getHotel().findRoomByNumber(roomNumber).getBeds()) {
                        currentFreeRoomNumber = roomNumber;
                        currentFreeBeds = getHotel().findRoomByNumber(roomNumber).getBeds();
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

        //print result
        if(currentFreeRoomNumber == 0){
            List<HotelRoom> potentialSwaps = new ArrayList<>();
            for(HotelRoom room : getHotel().getRooms()){
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

