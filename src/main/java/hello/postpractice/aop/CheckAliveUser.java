package hello.postpractice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //런타임시에 체크해야하므로
@Target(ElementType.METHOD) //method에서 선언
public @interface CheckAliveUser {
}
