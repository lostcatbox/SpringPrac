package hello.postpractice.controller;

import hello.postpractice.config.JwtProvider;
import hello.postpractice.domain.UserLoginResponseDto;
import hello.postpractice.domain.UserSignupRequestDto;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.ResponseService;
import hello.postpractice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<String> login(
            @ApiParam(value = "로그인 아이디 : 이메일", required = true) @RequestBody Map<String, String> loginMap) {
        String email = loginMap.get("username");
        String password = loginMap.get("password");
        UserLoginResponseDto userLoginDto = userService.login(email, password);

        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getId()), userLoginDto);

        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup( @RequestBody Map<String, String> signupMap){
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
}
