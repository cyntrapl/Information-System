package commands.hotel_commands;

import java.util.Scanner;

/**
 * Класът Unavailable отговаря за заетостта на стая.
 * Той разширява HotelCommand и преписва метода execute, за да извърши операцията за заетост на стая.
 */
public class Unavailable extends HotelCommand {
    private CheckIn checkIn;

    /**
     * Конструира нов обект Unavailable с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Unavailable(Scanner scanner) {
        super(scanner);
        this.checkIn = new CheckIn(scanner);
    }

    /**
     * Изпълнява операцията за заетост на стая.
     * Този метод отговаря за проверката дали даден файл е отворен, като извиква метода execute на CheckIn.
     */
    @Override
    public void execute(){
        checkIn.execute(true);
    }
}
