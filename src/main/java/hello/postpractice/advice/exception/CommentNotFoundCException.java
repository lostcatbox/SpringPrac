package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentNotFoundCException extends RuntimeException{

    public CommentNotFoundCException(String message) {
        super(message);
    }
}
