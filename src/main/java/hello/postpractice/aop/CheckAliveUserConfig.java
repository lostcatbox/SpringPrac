package hello.postpractice.aop;

import hello.postpractice.advice.exception.UserNotFoundCException;
import hello.postpractice.domain.User;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class CheckAliveUserConfig {

    private final UserRepository userRepository;

    @Pointcut("@annotation(hello.postpractice.aop.CheckAliveUser)") //해당 에너테이션이 있는경우 하위 함수실행
    private void check(){}

    @Before("check()")
    public void before(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((User) principal).getEmail();
        userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundCException("유저없음"));
    }

}
