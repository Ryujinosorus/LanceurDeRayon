package exceptions;

public class InvalidEntryException extends Exception {

    public InvalidEntryException() {
        super();
    }

    public InvalidEntryException(String message) {
        super(message);
    }
}
