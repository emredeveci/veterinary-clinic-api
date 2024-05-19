package deveci.veterinaryclinicapi.core.exception;

public class ExistingRecordsException extends RuntimeException {
    public ExistingRecordsException(String message) {
        super(message);
    }
}
