package hello.postpractice.controller;

import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.PostDto;
import hello.postpractice.domain.User;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.CommentService;
import hello.postpractice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequestMapping("/basic/post/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    private final ResponseService responseService;

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
    public SingleResult<CommentDto> commentSave(@PathVariable Long postId,
                                                @RequestBody CommentDto commentDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((User)principal).getEmail();

        return responseService.getSingleResult(commentService.commentSave(email, postId, commentDto));
    }

    /*
    댓글 update
     */
    @PutMapping
    public SingleResult<CommentDto> update(@RequestBody CommentDto dto){
        Long commentId = dto.getId();
        commentService.update(commentId, dto);
        return responseService.getSingleResult(dto);
    }
    /*
    댓글 delete
     */
    @DeleteMapping("/{commentId}")
    public HttpStatus delete(@PathVariable Long postId,@PathVariable Long commentId){
        commentService.delete(commentId);
        return HttpStatus.OK;
    }
}
