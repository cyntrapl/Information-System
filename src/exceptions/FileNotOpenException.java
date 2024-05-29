package exceptions;

public class FileNotOpenException extends Exception {
    public FileNotOpenException(String message) {
        super(message);
    }
}