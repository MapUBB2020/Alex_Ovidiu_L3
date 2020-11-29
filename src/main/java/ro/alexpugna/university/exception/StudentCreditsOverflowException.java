package ro.alexpugna.university.exception;

public class StudentCreditsOverflowException extends ProgramException {
    public StudentCreditsOverflowException(String message) {
        super(message);
    }

    public StudentCreditsOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
