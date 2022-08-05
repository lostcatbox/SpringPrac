package hello.postpractice.advice.exception;

public class CommentNotFoundCException extends RuntimeException{
    public CommentNotFoundCException() {
    }

    public CommentNotFoundCException(String message) {
        super(message);
    }
}
