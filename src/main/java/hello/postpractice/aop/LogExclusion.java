package hello.postpractice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 로그를 제외하는 에너테이션이므로 메서드위에 구현할것
@Retention(RetentionPolicy.RUNTIME) // 실행동안 유지해야하므로
public @interface LogExclusion {

}