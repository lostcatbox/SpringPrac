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

import java.util.Map;

@Api(tags = "1. SignUp / LogIn")
@RequiredArgsConstructor
@RestController("/")
public class SignController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public SingleResult<String> login(
            @ApiParam(value = "로그인 아이디 : 이메일", required = true) @RequestBody Map<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");
        UserLoginResponseDto userLoginDto = userService.login(username, password);

        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getId()), userLoginDto.getRoles());
        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @ApiParam(value = "회원 가입 아이디 : 이메일", required = true) @RequestParam String email,
            @ApiParam(value = "회원 가입 비밀번호", required = true) @RequestParam String password,
            @ApiParam(value = "회원 가입 이름", required = true) @RequestParam String username,
            @ApiParam(value = "회원 가입 닉네임", required = true) @RequestParam String nickname) {

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .nickname(nickname)
                .build();
        Long signupId = userService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }
}
