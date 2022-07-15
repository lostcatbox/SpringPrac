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
    public Long savePost(String email, PostDto postDto){
        User user = userRepository.findByEmail(email).get();
        postDto.setUser(user);

        //postDto에 User까지 반영후, 그다음에 Entity로 전환 후 저장
        Post post = postDto.toEntity();
        postRepository.save(post);

        return post.getId();
    }
    public Long editPost(Long id, PostDto postDto) {
        //회원을 확인하기위해 다시 디비조회?
        Post post = postRepository.findById(id).get();
        postDto.setUser(post.getUser());
        return postRepository.save(postDto.toEntity()).getId();
    }
    public void deletePost(PostDto postDto) {
        postRepository.delete(postDto.toEntity());
    }
    @Transactional
    public List<PostDto> getPostList() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post posting : postList) {
            PostDto postDto = PostDto.builder()
                    .id(posting.getId())
                    .postName(posting.getPostName())
                    .content(posting.getContent())
                    .createdDate(posting.getCreatedDate())
                    .modifiedDate(posting.getModifiedDate())
                    .user(posting.getUser())
                    .build();
            postDtoList.add(postDto);
        }
        return postDtoList;
    }
    @Transactional
    public PostDto getPost(Long id){
        Post posting = postRepository.findById(id).get();
        PostDto postDto = PostDto.builder()
                .id(posting.getId())
                .postName(posting.getPostName())
                .content(posting.getContent())
                .createdDate(posting.getCreatedDate())
                .modifiedDate(posting.getModifiedDate())
                .user(posting.getUser())
                .build();
        return postDto;
    }
    @Transactional
    public PostResponseDto getPost2(Long id){
        Post posting = postRepository.findById(id).get();
        PostResponseDto postDto = new PostResponseDto(posting);
        return postDto;
    }
}
