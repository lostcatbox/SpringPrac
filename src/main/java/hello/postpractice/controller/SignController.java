package hello.postpractice.controller;

import hello.postpractice.advice.exception.DataFieldInvalidCException;
import hello.postpractice.config.JwtProvider;
import hello.postpractice.domain.User;
import hello.postpractice.domain.UserLoginResponseDto;
import hello.postpractice.domain.UserSignupRequestDto;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.ResponseService;
import hello.postpractice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "1. SignUp / LogIn")
@RequiredArgsConstructor
@RestController("/")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class SignController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public SingleResult<Map> login(@RequestBody Map<String, String> loginMap) {

        String email = loginMap.get("email");
        String password = loginMap.get("password");

        UserLoginResponseDto userLoginDto = userService.login(email, password);
        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getId()), userLoginDto.getAuth());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("Authorization", token);

        return responseService.getSingleResult(tokenMap);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(@RequestBody Map<String, String> signupMap){
        String email = signupMap.get("email");
        String password = signupMap.get("password");
        String nickname = signupMap.get("nickname");
        String auth = signupMap.get("auth");
        if (auth.isEmpty()) {
            auth = "ROLE_USER"; //""이라면 ROLE_USER 로 default
        } else {
            auth = signupMap.get("auth");  //"ROLE_USER,ROLE_ADMIN" 이런식으로 들어옴
        }

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .auth(auth)
                .build();
        Long signupId = userService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }
    @PostMapping("/logout")
    public CommonResult logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            authentication.setAuthenticated(false);
            new SecurityContextLogoutHandler().logout(request,response,authentication);
            request.getSession().invalidate();
            return responseService.getSuccessResult();
        } else {
            throw new RuntimeException("로그인안되어있음");
        }
    }
}
