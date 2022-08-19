package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordFailCException extends RuntimeException{
    
    public PasswordFailCException(String message) {
        super(message);
    }
}
