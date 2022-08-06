package hello.postpractice.service;

import hello.postpractice.advice.exception.CommentNotFoundCException;
import hello.postpractice.advice.exception.PostNotFoundCException;
import hello.postpractice.advice.exception.UnMatchedUserCException;
import hello.postpractice.advice.exception.UserNotFoundCException;
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
                new PostNotFoundCException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + postId));
        List<CommentDto> commentList = post.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
        return commentList;
    }
    @Transactional
    public CommentDto get(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundCException("해당 id댓글없음"));
        CommentDto commentDto = new CommentDto(comment);
        return commentDto;
    }

    /* CREATE */
    @Transactional
    public CommentDto commentSave(String email, Long postId, CommentDto commentDto) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new UserNotFoundCException("해당유저없음"+email));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundCException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + postId));

        commentDto.setUser(user);
        commentDto.setPost(post);

        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);

        return commentDto;
    }
    /* Update */
    @Transactional
    public void update(String email, CommentDto commentDto){
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() ->
                new CommentNotFoundCException("해당 댓글이 존재하지 않습니다"));
        if (!(comment.getUser().getEmail().equals(email))) { //같은 유저 인지 체크
            throw new UnMatchedUserCException("권한 User가 아닙니다");
        }
        comment.update(commentDto.getComment());

    }
    /* Delete */
    @Transactional
    public void delete(String email,Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundCException("해당 댓글이 존재하지 않습니다"));
        if (!(comment.getUser().getEmail().equals(email) )) { //같은 유저 인지 체크
            throw new UnMatchedUserCException("권한 User가 아닙니다");
        }
        commentRepository.delete(comment);
    }
}
