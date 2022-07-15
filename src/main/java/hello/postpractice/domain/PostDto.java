package hello.postpractice.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


//Controller와 Service 사이에서 데이터를 주고받는 DTO를 구현한다
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String postName;
    private String content;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Post toEntity(){
        Post post = Post.builder()
                .id(id)
                .postName(postName)
                .content(content)
                .user(user)
                .build();
        return post;
    }


    @Builder
    public PostDto(Long id, String postName, String content, LocalDateTime createdDate, LocalDateTime modifiedDate,User user){
        this.id = id;
        this.postName = postName;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.user = user;
    }
}
