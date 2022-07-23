package hello.postpractice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;

    private String nickname;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.nickname = user.getNickname();

    }
}
