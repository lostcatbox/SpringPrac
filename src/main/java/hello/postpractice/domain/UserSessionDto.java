package hello.postpractice.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String email;
    private String password;
    private String nickname;
    private String role;

    /* Entity -> Dto */
    public UserSessionDto(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
