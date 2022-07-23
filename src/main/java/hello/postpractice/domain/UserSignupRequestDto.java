package hello.postpractice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;

@Getter
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String username;
    private String nickname;

    @Builder
    public UserSignupRequestDto(String email, String password, String username, String nickname) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .username(username)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
