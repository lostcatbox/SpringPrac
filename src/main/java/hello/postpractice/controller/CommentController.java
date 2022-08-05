package hello.postpractice.controller;

import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.PostDto;
import hello.postpractice.domain.User;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.ListResult;
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
    public ListResult<CommentDto> getcomments(@PathVariable Long postId){
        List<CommentDto> commentList = commentService.getList(postId);
        return responseService.getListResult(commentList);
    }
    @GetMapping("/{commentId}")
    public SingleResult<CommentDto> getcomment(@PathVariable Long postId, @PathVariable Long commentId){
        CommentDto commentDto = commentService.get(commentId);
        return responseService.getSingleResult(commentDto);
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
        String email = getCurrentUserEmail();
        commentService.update(email, dto);
        return responseService.getSingleResult(dto);
    }
    /*
    댓글 delete
     */
    @DeleteMapping("/{commentId}")
    public CommonResult delete(@PathVariable Long postId, @PathVariable Long commentId){
        String email = getCurrentUserEmail();
        commentService.delete(email, commentId);
        return responseService.getSuccessResult();
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((User) principal).getEmail();
        return email;
    }
}
