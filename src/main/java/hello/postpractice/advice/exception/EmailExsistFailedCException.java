package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailExsistFailedCException extends RuntimeException {

    public EmailExsistFailedCException(String message) {
        super(message);
    }
}
