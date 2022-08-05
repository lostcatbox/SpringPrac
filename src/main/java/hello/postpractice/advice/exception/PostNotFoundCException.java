package hello.postpractice.advice.exception;

public class PostNotFoundCException extends RuntimeException{
    public PostNotFoundCException() {
    }

    public PostNotFoundCException(String message) {
        super(message);
    }
}
