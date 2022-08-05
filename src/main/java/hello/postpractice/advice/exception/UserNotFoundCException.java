package hello.postpractice.advice.exception;

public class UserNotFoundCException extends RuntimeException {
        public UserNotFoundCException() {
                super();
        }

        public UserNotFoundCException(String message) {
                super(message);
        }
}
