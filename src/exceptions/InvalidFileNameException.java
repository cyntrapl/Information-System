package exceptions;

/**
 * Класът InvalidFileNameException е клас за обектите, които хвърлят грешка при невалидно име на файл.
 */
public class InvalidFileNameException extends Exception{
    public InvalidFileNameException(String message) {
        super(message);
    }
}
