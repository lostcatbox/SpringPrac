package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailNotFailedCException extends RuntimeException {
    public EmailNotFailedCException(String message) {
        super(message);
    }
    public EmailNotFailedCException(String message, Throwable cause) {
        super(message, cause);
    }
}
