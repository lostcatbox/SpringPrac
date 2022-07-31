package hello.postpractice.controller.api;

import hello.postpractice.domain.*;
import hello.postpractice.model.response.CommonResult;
import hello.postpractice.model.response.ListResult;
import hello.postpractice.model.response.SingleResult;
import hello.postpractice.service.PostService;
import hello.postpractice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    public SingleResult<PostDto> create(@RequestBody PostDto postDto){
        // jwt에서 작성자 넘겨주는 법 맞음?
        System.out.println("postDto = " + postDto);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return responseService.getSingleResult(postService.savePost(name, postDto));
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
