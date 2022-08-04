package hello.postpractice.advice.exception;

public class EmailNotFailedCException extends RuntimeException {
    public EmailNotFailedCException() {
        super();
    }
    public EmailNotFailedCException(String message) {
        super(message);
    }
    public EmailNotFailedCException(String message, Throwable cause) {
        super(message, cause);
    }
}
