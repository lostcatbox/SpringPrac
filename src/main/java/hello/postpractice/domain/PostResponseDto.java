package hello.postpractice.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id;

    private String postName;
    private String content;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<CommentDto> comments;

    /* Entity -> Dto*/
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.postName = post.getPostName();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.user = post.getUser();
        this.comments = post.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    }

}
