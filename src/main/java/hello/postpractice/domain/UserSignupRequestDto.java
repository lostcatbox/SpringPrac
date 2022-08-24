package hello.postpractice.domain;

import lombok.Builder;
import lombok.Getter;

import javax.management.relation.Role;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
public class UserSignupRequestDto {

    private String email;
    private String password;
    private String nickname;

    private String auth;

    @Builder
    public UserSignupRequestDto(String email, String password, String nickname, String auth) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.auth = auth;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .auth(auth)
                .build();
    }
}
