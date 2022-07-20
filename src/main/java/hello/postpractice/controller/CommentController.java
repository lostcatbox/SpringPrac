package hello.postpractice.controller;

import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.PostResponseDto;
import hello.postpractice.domain.User;
import hello.postpractice.domain.UserSessionDto;
import hello.postpractice.repository.PostRepository;
import hello.postpractice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/basic/post/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getcomments(@PathVariable Long postId){
        List<CommentDto> commentList = commentService.getList(postId);
        return commentList;
    }
    @GetMapping("/{commentId}")
    public CommentDto getcomment(@PathVariable Long postId, @PathVariable Long commentId){
        return commentService.get(commentId);
    }

    @PostMapping
    public HttpStatus commentSave(@PathVariable Long postId, CommentDto commentDto, HttpSession session){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        String email = user.getEmail();
        commentService.commentSave(email, postId, commentDto);
        return HttpStatus.OK;
    }

    /*
    댓글 update
     */
    @PutMapping({"/{commentId}"})
    public CommentDto update(@PathVariable Long postId,@PathVariable Long commentId, CommentDto dto){
        commentService.update(commentId, dto);
        return commentService.get(commentId);
    }
    /*
    댓글 delete
     */
    @DeleteMapping("/{commentId}")
    public HttpStatus delete(@PathVariable Long postId,@PathVariable Long commentId, CommentDto dto){
        commentService.delete(postId);
        return HttpStatus.OK;
    }
}
