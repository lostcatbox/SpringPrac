package hello.postpractice.controller.api;

import hello.postpractice.domain.*;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.ListResult;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.PostService;
import hello.postpractice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("basic/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ResponseService responseService;

    @GetMapping
    public ListResult<PostDto> getposts(Model model){
        List<PostDto> postDtoList = postService.getPostList();
        return responseService.getListResult(postDtoList);
    }

    @GetMapping("/{id}")
    public SingleResult<PostResponseDto> getpost(@PathVariable Long id, Model model, HttpSession session){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        PostResponseDto postResponseDto = postService.getResponseDtoPost(id);
        return responseService.getSingleResult(postResponseDto);
//
//        /*유저관련*/
//        if (!ObjectUtils.isEmpty(user)){
//            model.addAttribute("user", user.getEmail());
//
//            //게시판 작성자 본인인지 확인
//            if (postResponseDto.getUser().getEmail().equals(user.getEmail())) {
//                model.addAttribute("writer", true);
//            }
//        }
    }
    @PostMapping
    public SingleResult<PostDto> addpost(HttpSession session, @RequestBody PostDto postDto){
        UserSessionDto user = (UserSessionDto) session.getAttribute("user");
        return responseService.getSingleResult(postService.savePost(user.getEmail(), postDto));
    }
    @PutMapping("/{id}")
    public SingleResult<PostDto> edit(@PathVariable Long id, @RequestBody PostDto postDto){
        return responseService.getSingleResult(postService.editPost(id, postDto));
    }
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        postService.deletePost(postDto);
        return responseService.getSuccessResult();
    }
}
