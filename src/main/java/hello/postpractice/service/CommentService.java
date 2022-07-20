package hello.postpractice.service;

import hello.postpractice.domain.Comment;
import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.Post;
import hello.postpractice.domain.User;
import hello.postpractice.repository.CommentRepository;
import hello.postpractice.repository.PostRepository;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public List<CommentDto> getList(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + postId));
        List<CommentDto> commentList = post.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        return commentList;
    }
    @Transactional
    public CommentDto get(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 id댓글없음"));
        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }

    /* CREATE */
    @Transactional
    public Long commentSave(String email, Long id, CommentDto commentDto) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new IllegalArgumentException("해당 id로 유저 없음"));
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));

        commentDto.setUser(user);
        commentDto.setPost(post);

        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);

        return commentDto.getId();
    }
    /* Update */
    @Transactional
    public void update(Long commentId, CommentDto commentDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당댓글이 존재하지 않습니다"));
        comment.update(commentDto.getComment());
    }
    /* Delete */
    @Transactional
    public void delete(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
        commentRepository.delete(comment);
    }
}
