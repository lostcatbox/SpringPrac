package hello.postpractice.advice.exception;

public class PasswordFailCException extends RuntimeException{
    public PasswordFailCException() {
        super();
    }
    
    public PasswordFailCException(String message) {
        super(message);
    }
}
