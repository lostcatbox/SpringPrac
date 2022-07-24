package hello.postpractice.service;

import hello.postpractice.domain.PostDto;
import hello.postpractice.domain.Post;
import hello.postpractice.domain.PostResponseDto;
import hello.postpractice.domain.User;
import hello.postpractice.repository.PostRepository;
import hello.postpractice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    public PostDto savePost(String email, PostDto postDto){
        // 유저가 필요함. 정보를 jwt에서 가져와야할듯
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저없음"));
        postDto.setUser(user);

        //postDto에 User까지 반영후, 그다음에 Entity로 전환 후 저장
        Post post = postDto.toEntity();
        postRepository.save(post);

        return new PostDto(post);
    }
    public PostDto editPost(PostDto postDto) {
        //해당 postDto에서 id를 찾아 DB조회후 엔티티에 update함수를 활용해 처리
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을수없습니다"));
        post.update(postDto.getPostName(), postDto.getContent());
        postRepository.save(post);
        return new PostDto(post);
    }
    public void deletePost(PostDto postDto) {
        postRepository.delete(postDto.toEntity());
    }
    @Transactional
    public List<PostDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList) {
            PostDto postDto = new PostDto(post);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }
    @Transactional
    public PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("게시글 id 검색 실패: 해당 게시글이 존재하지 않습니다." + id));
        PostDto postDto = new PostDto(post);

        return postDto;
    }
    @Transactional
    public PostResponseDto getResponseDtoPost(Long id){
        Post posting = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글 id 검색 실패: 해당 게시글이 존재하지 않습니다." + id));
        PostResponseDto postDto = new PostResponseDto(posting);
        return postDto;
    }
}
