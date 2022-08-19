package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostNotFoundCException extends RuntimeException{

    public PostNotFoundCException(String message) {
        super(message);
    }
}
