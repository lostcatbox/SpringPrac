package hello.postpractice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//각 기능들은 뭐지?
// entity는 DB 테이블과 매핑되는 객체다
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)/* JPA에게 해당 Entity는 Auditing 기능을 사용함을 알립니다. */
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length =10,nullable = false)
    private String postName;

    @Column(columnDefinition ="TEXT", nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;


    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") //댓글 정렬
    private List<Comment> comments;

    @Builder  //매개변수를 builder 패턴으로 편리성,유연함을 줌
    public Post(Long id, String postName, String content, User user, List<Comment> comments){
        this.id = id;
        this.postName = postName;
        this.content = content;
        this.user = user;
        this.comments =comments;
    }

    /*
    게시글 수정
     */
    public void update(String postName,String content){
        this.postName = postName;
        this.content = content;
    }

}
