package exceptions;

/**
 * Класът FileNotOpenException е клас за обектите, които хвърлят грешка при опит за работа с файл, който не е отворен.
 */
public class FileNotOpenException extends Exception {
    public FileNotOpenException(String message) {
        super(message);
    }
}