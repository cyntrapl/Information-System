package exceptions;

/**
 * Класът InvalidDateRangeException е клас за обектите, които хвърлят грешка при невалиден диапазон на дати.
 */
public class InvalidDateRangeException extends Exception {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}