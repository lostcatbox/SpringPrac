package hello.postpractice.service;

import hello.postpractice.domain.User;
import hello.postpractice.domain.UserDto;
import hello.postpractice.domain.UserSessionDto;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final HttpSession session;

    /**
     * Spring Security 필수 메소드 구현
     * 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
     * 특히 session에 user객체를 넘김으로써, 시큐리티 세션에 유저 정보 저장 가능
     * @param email 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당사용자가 존재하지 않습니다.:" +email));

        // 시큐리티 세션에 유저 정보 저장
        session.setAttribute("user",new UserSessionDto(user));

        return user;

    }
    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long join(UserDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword())); // 비번 암호화

        return userRepository.save(infoDto.toEntity()).getId();
    }
}