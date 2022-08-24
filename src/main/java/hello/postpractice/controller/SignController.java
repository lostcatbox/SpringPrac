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
        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getId()), userLoginDto.getRoles());
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

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
        Long signupId = userService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){ //authentication은 인증이되어있는지?
            new SecurityContextLogoutHandler().logout(request,response,authentication); //로그아웃 핸들러를 호출해 로그아웃처리(authentication=null, session만료 등의 기능포함됨)
        } else {
            throw new RuntimeException("로그인필요");
        }
    }
}
