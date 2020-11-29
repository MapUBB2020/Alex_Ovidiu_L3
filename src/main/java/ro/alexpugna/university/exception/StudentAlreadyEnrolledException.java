package ro.alexpugna.university.exception;

public class StudentAlreadyEnrolledException extends ProgramException {
    public StudentAlreadyEnrolledException(String message) {
        super(message);
    }

    public StudentAlreadyEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }
}
