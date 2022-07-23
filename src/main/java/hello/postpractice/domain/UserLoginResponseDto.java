package hello.postpractice.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLoginResponseDto {
    private Long id;
    private List<String> roles;
    private LocalDateTime createdDate;

    public UserLoginResponseDto(User user) {
        this.id = user.getId();
        this.roles = user.getRoles();
        this.createdDate = user.getCreatedDate();
    }
}
