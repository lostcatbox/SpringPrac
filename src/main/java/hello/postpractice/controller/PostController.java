package hello.postpractice.controller;

import hello.postpractice.aop.LogExclusion;
import hello.postpractice.domain.*;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.ListResult;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.PostService;
import hello.postpractice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("basic/post")
@CrossOrigin(origins="*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ResponseService responseService;

    @GetMapping
    public ListResult<PostDto> postList(){
        List<PostDto> postDtoList = postService.getPostList();
        return responseService.getListResult(postDtoList);
    }

    @GetMapping("/{id}")
    public SingleResult<PostDto> getpost(@PathVariable Long id){
        PostDto PostDto = postService.getResponseDtoPost(id);
        return responseService.getSingleResult(PostDto);
    }
    @PostMapping
    public SingleResult<PostDto> create(@RequestBody PostDto postDto){
        String email = getCurrentUserEmail();
        return responseService.getSingleResult(postService.savePost(email, postDto));
    }
    @PutMapping()
    public SingleResult<PostDto> update(@RequestBody PostDto postDto){
        String email = getCurrentUserEmail();
        return responseService.getSingleResult(postService.editPost(email,postDto));
    }
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        String email = getCurrentUserEmail();
        postService.deletePost(email, postDto);
        return responseService.getSuccessResult();
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((User) principal).getEmail();
        return email;
    }
}

// post C -> S
// 인증 검사를 하는 플로우는 post랑은 상관없음.
// (Post C - > AuthService -> Post C ->) Post S
// 관심사 분리. 책임 분리
// post 엔티티에 대해서는 post C가 검증해야하지만, user에 대한 검증이므로 userAuthService를 따로만들엉야함
// 성공시 HTTP status로 충분하다 성공하면 200
// 실패시에만 message를 주는 것만 고려하면됨