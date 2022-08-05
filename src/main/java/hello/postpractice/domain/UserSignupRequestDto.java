package hello.postpractice.domain;

import lombok.Builder;
import lombok.Getter;

import javax.management.relation.Role;
import java.util.Collections;

@Getter
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String nickname;

    @Builder
    public UserSignupRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
