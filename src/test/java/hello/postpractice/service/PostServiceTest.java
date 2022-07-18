package hello.postpractice.service;

import hello.postpractice.domain.Post;
import hello.postpractice.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void getPostList() {
        //given
        List<Post> all = postRepository.findAll();

        //when
        Post post = all.get(0);
        //then
        System.out.println(post.getId());

    }
}