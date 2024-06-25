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
 * Abstract class CommandClass
 * Abstract class that implements the Command interface
 */
public abstract class CommandClass implements Command {
    private CurrentFile currentFile;
    private Scanner scanner;

    /**
     * Constructor for CommandClass
     * @param scanner Scanner object used to read user input
     */
    public CommandClass(Scanner scanner) {
        this.currentFile = CurrentFile.getInstance();
        this.scanner = scanner;
    }

    /**
     * Method that checks if a file is open
     * @throws FileNotOpenException if the file is not open
     */
    public void checkIfFileIsOpen() throws FileNotOpenException {
        if(currentFile.getCurrentFileName() == null){
            throw new FileNotOpenException("File not open!");
        }
    }

    /**
     * Method that checks if the number of arguments is valid
     * @param min minimum number of arguments
     * @param max maximum number of arguments
     * @return String[] array containing the parts of the input line
     * @throws InvalidNumberOfArgumentsException if the number of arguments is invalid
     */
    public String[] checkValidNumberOfArguments(int min, int max) throws InvalidNumberOfArgumentsException {
        String inputLine = scanner.nextLine();
        String[] parts = inputLine.split(" ");
        if (parts.length > max || parts.length < min) {
            throw new InvalidNumberOfArgumentsException("Invalid number of arguments.");
        }else return parts;
    }

    /**
     * Method that checks if a file name is valid
     * @param fileName String representing the file name
     * @throws InvalidFileNameException if the file name is invalid
     */
    public void checkIfValidFileName(String fileName) throws InvalidFileNameException {
        //regex moment
        Pattern pattern = Pattern.compile(".*\\.txt$");
        Matcher matcher = pattern.matcher(fileName);
        if (!matcher.matches()) {
            throw new InvalidFileNameException("Invalid file name! The file name must end with '.txt'.");
        }
    }

    /**
     * Getter for currentFile
     * @return CurrentFile object
     */
    public CurrentFile getCurrentFile() {
        return currentFile;
    }

    /**
     * Getter for scanner
     * @return Scanner object
     */
    public Scanner getScanner() {
        return scanner;
    }
}
