package hello.postpractice.advice.exception;

public class UnMatchedUserCException extends RuntimeException{
    public UnMatchedUserCException() {
    }

    public UnMatchedUserCException(String message) {
        super(message);
    }
}
