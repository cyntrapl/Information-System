package exceptions;

/**
 * Exception for when a file is not open
 */
public class FileNotOpenException extends Exception {
    public FileNotOpenException(String message) {
        super(message);
    }
}