package hello.postpractice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
// SecurityConfig에서 userpassword filter보다 앞에 등록
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    // request로 들어오는 Jwt의 유효성을 검증 - JwtProvider.validationToken() 을 필터로서 FilterChain에 추가
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        String requestTokenHeader = jwtProvider.resolveToken((HttpServletRequest) request); //http에서 Bearer+Token값 얻어옴(JWT토큰)
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {  // Bearer로 시작하는지확인
            String token = requestTokenHeader.substring(7);  //token값 추출
            jwtProvider.validationToken(token);// validationToken()으로 secret key로 시그니처 일치확인 및 만료일자 지낫는지확인함
            Authentication authentication = jwtProvider.getAuthentication(token); //token에서 get(userpk)하여 loadbyusername()실행하여, 해당 유저의 authentication 생성및반환
            SecurityContextHolder.getContext().setAuthentication(authentication); // authentication
        }
        filterChain.doFilter(request, response);
    }
}

