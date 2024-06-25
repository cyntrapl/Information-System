package commands.hotel_commands;

import java.util.Scanner;

/**
 * The Unavailable class is used to make a room unavailable for booking.
 * It extends the HotelCommand abstract class and implements the execute method.
 */
public class Unavailable extends HotelCommand {
    private CheckIn checkIn;

    /**
     * Constructor for Unavailable
     * @param scanner Scanner object used to read user input
     */
    public Unavailable(Scanner scanner) {
        super(scanner);
        this.checkIn = new CheckIn(scanner);
    }

    /**
     * Method that makes a room unavailable for booking
     */
    @Override
    public void execute(){
        checkIn.execute(true);
    }
}
