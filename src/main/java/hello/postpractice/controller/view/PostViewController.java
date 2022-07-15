package hello.postpractice.controller.view;

import hello.postpractice.domain.PostDto;
import hello.postpractice.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("basic/post/view")
public class PostViewController {

    private PostService postService;

    public PostViewController(PostService postService){

        this.postService = postService;
    }
    @GetMapping
    public String get(Model model){
        List<PostDto> postDtoList = postService.getPostList();
        model.addAttribute("postings", postDtoList);
        return "basic/items";
    }
    @GetMapping("/{id}/edit")
    public String getEditForm(@PathVariable Long id, Model model){
        PostDto postDto = postService.getPost(id);
        model.addAttribute("posting", postDto);
        return "basic/editForm";
    }
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

}
