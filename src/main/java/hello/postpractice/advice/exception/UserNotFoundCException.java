package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundCException extends RuntimeException {

        public UserNotFoundCException(String message) {
                super(message);
        }
}
