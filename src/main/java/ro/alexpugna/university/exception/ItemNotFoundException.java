package ro.alexpugna.university.exception;

public class ItemNotFoundException extends ProgramException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
