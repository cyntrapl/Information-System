package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Класът Availability отговаря за проверката на наличността на стаите.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за проверка на наличността.
 */
public class Availability extends HotelCommand {
    private ArrayList<Integer> availableRooms;
    private Date date;

    /**
     * Конструира нов обект Availability с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Availability(Scanner scanner) {
        super(scanner);
        availableRooms = new ArrayList<>();
    }

    /**
     * Изпълнява операцията за проверка на наличността.
     * Този метод отговаря за проверката дали даден файл е отворен, като получава избраната от потребителя дата,
     * и проверява всички стаи за наличност на избраната дата.
     */
    @Override
    public void execute() {

        availableRooms.clear();

        //check if number of arguments is valid
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(1, 2);
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


        //get date
        if(parts.length == 2){
            String dateString = parts[1];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
                return;
            }
        }else date = new Date();

        //get available rooms
        Scanner fileScanner = null;
        File file = new File("rooms.txt");
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        while(fileScanner.hasNextLine()) {
            availableRooms.add(Integer.parseInt(fileScanner.next()));
            fileScanner.nextLine();
        }

        //check if rooms are available
        for(HotelRoom hotelRoom : getHotel().getRooms()){
            if(date.after(hotelRoom.getReservation().getFromDate()) && date.before(hotelRoom.getReservation().getToDate())){
                availableRooms.remove((Integer)hotelRoom.getRoomNumber());
            }
        }

        //print available rooms
        System.out.println("Available rooms: ");
        for(Integer roomNumber : availableRooms){
            System.out.println(roomNumber);
        }
    }
}
