package hello.postpractice.advice.exception;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DataFieldInvalidCException extends RuntimeException{
    public DataFieldInvalidCException(String message) {
        super(message);
    }
    public DataFieldInvalidCException(List messageList) {
        super((String)messageList.stream().collect(Collectors.joining(","))); // List Stream을 String으로 join
    }

}
