package exceptions;

/**
 * Класът InvalidNumberOfArgumentsException е клас за обектите, които хвърлят грешка при невалиден брой аргументи.
 */
public class InvalidNumberOfArgumentsException extends Exception {
    public InvalidNumberOfArgumentsException(String message) {
        super(message);
    }
}