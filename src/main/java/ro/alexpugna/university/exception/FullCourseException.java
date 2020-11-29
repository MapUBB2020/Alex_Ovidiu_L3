package ro.alexpugna.university.exception;

public class FullCourseException extends ProgramException {
    public FullCourseException(String message) {
        super(message);
    }

    public FullCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
