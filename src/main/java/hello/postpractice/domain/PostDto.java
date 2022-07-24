package hello.postpractice.domain;

import lombok.*;

import java.io.Serializable;
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


    public PostDto(Post post){
        this.id = post.getId();
        this.postName = post.getPostName();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.user = post.getUser();
    }
}
