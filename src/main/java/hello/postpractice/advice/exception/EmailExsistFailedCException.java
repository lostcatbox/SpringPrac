package hello.postpractice.advice.exception;

public class EmailExsistFailedCException extends RuntimeException {
    public EmailExsistFailedCException() {
    }

    public EmailExsistFailedCException(String message) {
        super(message);
    }
}
