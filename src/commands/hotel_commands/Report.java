package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Класът Report отговаря за извеждането на отчет за заетостта на стаите.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за извеждане на отчет.
 */
public class Report extends HotelCommand {
    private Date fromDate;
    private Date toDate;

    /**
     * Конструира нов обект Report с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Report(Scanner scanner) {
        super(scanner);
    }

    /**
     * Изпълнява операцията за извеждане на отчет.
     * Този метод отговаря за проверката дали даден файл е отворен, като получава началната и крайната дата на периода,
     * за който се извежда отчета, и извежда отчет за заетостта на стаите за този период.
     */
    @Override
    public void execute() {
        //check if number of arguments is valid
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(3,3);
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

        //get dates
        String fromDateString = parts[1];
        String toDateString = parts[2];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = dateFormat.parse(fromDateString);
            toDate = dateFormat.parse(toDateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please enter date in format: yyyy-MM-dd");
            return;
        }

        //print report
        for(HotelRoom hotelRoom : getHotel().getRooms()){
            if ( ( toDate.before(hotelRoom.getReservation().getFromDate()) || fromDate.after(hotelRoom.getReservation().getToDate()) && !hotelRoom.isAvailable() ) ) {
                continue;
            }

            Date overlapStart = fromDate.before(hotelRoom.getReservation().getFromDate()) ? hotelRoom.getReservation().getFromDate() : fromDate;
            Date overlapEnd = toDate.after(hotelRoom.getReservation().getToDate()) ? hotelRoom.getReservation().getToDate() : toDate;
            System.out.println("Room " + hotelRoom.getRoomNumber() + " checked in for " + ((overlapEnd.getTime() - overlapStart.getTime()) / (1000 * 60 * 60 * 24) + 1) + " days");
        }
    }
}
