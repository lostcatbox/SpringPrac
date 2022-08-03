package hello.postpractice.controller.api;

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
    public SingleResult<PostResponseDto> getpost(@PathVariable Long id){
        PostResponseDto postResponseDto = postService.getResponseDtoPost(id);
        return responseService.getSingleResult(postResponseDto);
    }
    @PostMapping
    public SingleResult<PostDto> create(@RequestBody PostDto postDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((User) principal).getEmail();
        return responseService.getSingleResult(postService.savePost(email, postDto));
    }
    @PutMapping()
    public SingleResult<PostDto> update(@RequestBody PostDto postDto){
        return responseService.getSingleResult(postService.editPost(postDto));
    }
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Long id){
        PostDto postDto = postService.getPost(id);
        postService.deletePost(postDto);
        return responseService.getSuccessResult();
    }
}
