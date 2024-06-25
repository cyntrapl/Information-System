package exceptions;

/**
 * Exception for when a file is not open
 */
public class InvalidNumberOfArgumentsException extends Exception {
    public InvalidNumberOfArgumentsException(String message) {
        super(message);
    }
}