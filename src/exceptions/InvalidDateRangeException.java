package exceptions;

/**
 * Exception for when the date range is invalid
 */
public class InvalidDateRangeException extends Exception {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}