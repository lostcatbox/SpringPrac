package hello.postpractice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String nickname;
    private String password;

    private String role;

    public User toEntity(){
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .role(role)
                .password(password)
                .build();
        return user;

    }
}
