package exceptions;

/**
 * Exception for when a file is not open
 */
public class InvalidFileNameException extends Exception{
    public InvalidFileNameException(String message) {
        super(message);
    }
}
