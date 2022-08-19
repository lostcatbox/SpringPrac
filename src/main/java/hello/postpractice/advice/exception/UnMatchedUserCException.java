package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnMatchedUserCException extends RuntimeException{

    public UnMatchedUserCException(String message) {
        super(message);
    }
}
