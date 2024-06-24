package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidDateRangeException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Класът Find отговаря за намирането на свободна стая.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за намиране на свободна стая.
 */
public class Find extends HotelCommand {
    private Date fromDate;
    private Date toDate;
    private int roomNumber;
    private int beds;
    private int currentFreeRoomNumber = 0;
    private int currentFreeBeds = 0;

    /**
     * Конструира нов обект Find с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Find(Scanner scanner) {
        super(scanner);
    }

    /**
     * Изпълнява операцията за намиране на свободна стая.
     * Този метод отговаря за проверката дали даден файл е отворен, като получава броя на леглата,
     * началната и крайната дата на периода, за който се търси свободна стая.
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

        //init
        currentFreeBeds = Integer.MAX_VALUE;
        currentFreeRoomNumber = 0;

        int bedsInput = Integer.parseInt(parts[1]);

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
        Scanner fileScanner;
        File file = new File("rooms.txt");
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
            if(beds < bedsInput || currentFreeBeds < beds) continue;
            if(getHotel().findRoomByNumber(roomNumber) != null){
                HotelRoom tempRoom = getHotel().findRoomByNumber(roomNumber);
                boolean isAvailableAndRightDates = ( tempRoom.getReservation().getFromDate().after(toDate) || tempRoom.getReservation().getToDate().before(fromDate) ) ;
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
        if(currentFreeRoomNumber == 0){
            System.out.println("No room found!");
        }else System.out.println("Room " + currentFreeRoomNumber + " has been found with " + currentFreeBeds + " beds!");
    }
}
