package commands.hotel_commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;
import hotel.HotelFileHandler;

import java.util.Scanner;

/**
 * Класът Checkout отговаря за изписването на гостите от стаите.
 * Той разширява HotelCommand и пренаписва метода execute, за да извърши операцията за освобождаване на стая.
 */
public class Checkout extends HotelCommand {
    private int roomNumber;
    private HotelFileHandler hotelFileHandler;

    /**
     * Конструира нов обект Checkout с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Checkout(Scanner scanner) {
        super(scanner);
        this.hotelFileHandler = new HotelFileHandler();
    }

    /**
     * Изпълнява операцията за освобождаване на стая.
     * Този метод отговаря за проверката дали даден файл е отворен, като получава номера на стаята,
     * и освобождава стаята, ако тя съществува.
     */
    @Override
    public void execute() {

        //check if number of arguments is valid
        String[] parts;
        try {
            parts = checkValidNumberOfArguments(2, 2);
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

        if(roomNumber > getHotel().getNumberOfRooms()){
            System.out.println("Invalid room number!");
            return;
        }

        //checkout
        if(getHotel().findRoomByNumber(roomNumber) != null){
            getHotel().removeRoomByRoomNumber(roomNumber);
            hotelFileHandler.saveRoomsToFile();
            System.out.println("Successfully checked out " + roomNumber);
        }else{
            System.out.println("Room not found!");
        }
    }
}
