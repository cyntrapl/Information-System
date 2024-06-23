package commands;

import exceptions.FileNotOpenException;
import exceptions.InvalidFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import interfaces.Command;
import singletons.CurrentFile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Абстрактен клас CommandClass, който имплементира интерфейса Command.
 * Този клас съдържа методи, които се използват от всички команди.
 */
public abstract class CommandClass implements Command {
    private CurrentFile currentFile;
    private Scanner scanner;

    /**
     * Конструира нов обект CommandClass с посочения обект Scanner.
     * @param scanner обектът Scanner, използван за въвеждане от потребителя.
     */
    public CommandClass(Scanner scanner) {
        this.currentFile = CurrentFile.getInstance();
        this.scanner = scanner;
    }

    /**
     * Проверява дали файлът е отворен.
     * @throws FileNotOpenException хвърля изключение, ако файлът не е отворен.
     */
    public void checkIfFileIsOpen() throws FileNotOpenException {
        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }
    }

    /**
     * Проверява дали броят на аргументите е валиден.
     * @param min минимален брой аргументи
     * @param max максимален брой аргументи
     * @return масив от стрингове, съдържащи аргументите
     * @throws InvalidNumberOfArgumentsException хвърля изключение, ако броят на аргументите не е валиден
     */
    public String[] checkValidNumberOfArguments(int min, int max) throws InvalidNumberOfArgumentsException {
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");
        if (parts.length > max || parts.length < min) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments.");
        }else return parts;
    }

    public void checkIfValidFileName(String fileName) throws InvalidFileNameException {
        //regex moment
        Pattern pattern = Pattern.compile(".*\\.txt$");
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            throw new InvalidFileNameException("Invalid file name! The file name must end with '.txt'.");
        }
    }

    /**
     * Връща обекта CurrentFile.
     * @return обекта CurrentFile
     */
    public CurrentFile getCurrentFile() {
        return currentFile;
    }

    /**
     * Връща обекта Scanner.
     * @return обекта Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }
}
