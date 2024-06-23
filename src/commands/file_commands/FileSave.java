package commands.file_commands;

import commands.CommandClass;
import exceptions.FileNotOpenException;
import exceptions.InvalidNumberOfArgumentsException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класът FileSave отговаря за записването на отворения в момента файл.
 * Той разширява класа CommandClass и пренаписва метода execute, за да извърши операцията по запазване на файла.
 */
public class FileSave extends CommandClass {

    /**
     * Конструира нов обект FileSave с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public FileSave(Scanner scanner) {
        super(scanner);
    }

    /**
     * Изпълнява операцията за запазване на файла.
     * Този метод отговаря за проверката дали е отворен файл, като получава името на файла,
     * и записване на текущото съдържание на файла в него.
     */
    @Override
    public void execute() {
        //check if number of arguments is valid
        String fileName;
        try {
            fileName = checkValidNumberOfArguments(1, 1)[1];
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

        //save file
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            writer.write(getCurrentFile().getFileContent().toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        System.out.println("Successfully saved " + fileName);
    }
}
