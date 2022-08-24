package hello.postpractice.domain;

import lombok.Builder;
import lombok.Getter;

import javax.management.relation.Role;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
                .roles(new ArrayList<>(Arrays.asList("ROLE_USER","ROLE_ADMIN"))) //역할두개?
                .build();
    }
}
