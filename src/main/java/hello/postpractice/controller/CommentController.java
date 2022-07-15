package hello.postpractice.controller;

import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.User;
import hello.postpractice.domain.UserSessionDto;
import hello.postpractice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/basic/post/{id}/comments")
    public String  commentSave(@PathVariable Long id, CommentDto commentDto, HttpSession session){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        String email = user.getEmail();
        commentService.commentSave(email, id, commentDto);
        return "redirect:/basic/post/"+Long.toString(id);
    }

    /*
    댓글 update
     */
    @PutMapping({"basic/post/{id}/comments/{commentId}"})
    public String update(@PathVariable Long id,@PathVariable Long commentId, CommentDto dto){
        commentService.update(commentId, dto);
        return "redirect:/basic/post/"+Long.toString(id);
    }
    /*
    댓글 delete
     */
    @DeleteMapping("basic/post/{id}/comments/{commentId}")
    public String delete(@PathVariable Long id,@PathVariable Long commentId, CommentDto dto){
        commentService.delete(commentId);
        return "redirect:/basic/post/" + Long.toString(id);
    }
}
