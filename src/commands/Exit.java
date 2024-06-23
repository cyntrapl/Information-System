package commands;

import exceptions.InvalidNumberOfArgumentsException;

import java.util.Scanner;

/**
 * Класът Exit е клас наследник на CommandClass и се използва за излизане от конзолата.
 * Той съдържа метода execute, който извършва излизането от конзолата.
 */
public class Exit extends CommandClass{

    /**
     * Конструира нов обект Exit със подаден обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public Exit(Scanner scanner) {
        super(scanner);
    }

    /**
     * Изпълнява операцията за излизане от конзолата.
     * Този метод извежда съобщение за излизане и излиза от конзолата.
     */
    @Override
    public void execute() {
        try {
            checkValidNumberOfArguments(1, 1);
        } catch (InvalidNumberOfArgumentsException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Exiting the console...");
        System.exit(0);
    }
}