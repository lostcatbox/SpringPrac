package hello.postpractice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
    private String email;

    private String nickname;

    @Builder
    public UserRequestDto(String email, String username, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
