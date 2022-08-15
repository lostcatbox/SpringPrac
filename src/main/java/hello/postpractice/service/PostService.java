package hello.postpractice.service;

import hello.postpractice.advice.exception.PostNotFoundCException;
import hello.postpractice.advice.exception.UnMatchedUserCException;
import hello.postpractice.advice.exception.UserNotFoundCException;
import hello.postpractice.aop.CheckAliveUser;
import hello.postpractice.domain.PostDto;
import hello.postpractice.domain.Post;
import hello.postpractice.domain.PostResponseDto;
import hello.postpractice.domain.User;
import hello.postpractice.repository.PostRepository;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
 //모든 유저 라이브 유저인지 먼저체크
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @CheckAliveUser
    public PostDto savePost(String email, PostDto postDto){
        postDto.setUser(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundCException("유저없음")));

        //postDto에 User까지 반영후, 그다음에 Entity로 전환 후 저장
        Post post = postDto.toEntity();
        postRepository.save(post);

        return new PostDto(post);
    }
    @CheckAliveUser
    public PostDto editPost(String email, PostDto postDto) {
        //해당 postDto에서 id를 찾아 DB조회후 엔티티에 update함수를 활용해 처리
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundCException("게시글을 찾을수없습니다"));
        if (!(post.getUser().getEmail().equals(email))) { //같은 유저 인지 체크
            throw new UnMatchedUserCException("권한 USER 아닙니다");
        }
        post.update(postDto.getPostName(), postDto.getContent());
        postRepository.save(post);
        return new PostDto(post);
    }
    @CheckAliveUser
    public void deletePost(String email,PostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundCException("게시글을 찾을수없습니다"));
        if (!(post.getUser().getEmail().equals(email))) { //같은 유저 인지 체크
            throw new UnMatchedUserCException("권한 USER 아닙니다");
        }
        postRepository.delete(post);
    }
    @CheckAliveUser
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
    @CheckAliveUser
    @Transactional
    public PostDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->
                new PostNotFoundCException("게시글 id 검색 실패: 해당 게시글이 존재하지 않습니다." + id));
        PostDto postDto = new PostDto(post);

        return postDto;
    }
    @CheckAliveUser
    @Transactional
    public PostResponseDto getResponseDtoPost(Long id){
        Post posting = postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundCException("게시글 id 검색 실패: 해당 게시글이 존재하지 않습니다." + id));
        PostResponseDto postDto = new PostResponseDto(posting);
        return postDto;
    }
}
