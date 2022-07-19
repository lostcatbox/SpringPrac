package hello.postpractice.controller.api;

import hello.postpractice.domain.CommentDto;
import hello.postpractice.domain.PostDto;
import hello.postpractice.domain.PostResponseDto;
import hello.postpractice.domain.UserSessionDto;
import hello.postpractice.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("basic/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String getpost(@PathVariable Long id, Model model, HttpSession session){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        PostResponseDto postResponseDto = postService.getResponseDtoPost(id);
        List<CommentDto> comments = postResponseDto.getComments();

        /*댓글관련*/
        if (!ObjectUtils.isEmpty(comments)){
            model.addAttribute("comments", comments);
        }
        /*유저관련*/
        if (!ObjectUtils.isEmpty(user)){
            model.addAttribute("user", user.getEmail());

            //게시판 작성자 본인인지 확인
            if (postResponseDto.getUser().getEmail().equals(user.getEmail())) {
                model.addAttribute("writer", true);
            }
        }
        model.addAttribute("posting", postResponseDto);

        return "basic/item";
    }
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute PostDto postDto){
        postService.editPost(id,postDto);
        return "redirect:/basic/post/{id}";
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        postService.deletePost(postDto);
        return "redirect:/basic/post/view";
    }

    @PostMapping("/add")
    public String postadd(PostDto postDto, HttpSession session){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        postService.savePost(user.getEmail(),postDto);
        return "redirect:/basic/post/view";
    }
}
