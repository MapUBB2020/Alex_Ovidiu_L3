package ro.alexpugna.university.exception;

public class ProgramException extends Exception {
    public ProgramException(String message) {
        super(message);
    }

    public ProgramException(String message, Throwable cause) {
        super(message, cause);
    }
}
