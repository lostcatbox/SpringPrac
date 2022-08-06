package hello.postpractice.service;

import hello.postpractice.advice.exception.EmailExsistFailedCException;
import hello.postpractice.advice.exception.EmailNotFailedCException;
import hello.postpractice.advice.exception.PasswordFailCException;
import hello.postpractice.advice.exception.UserNotFoundCException;
import hello.postpractice.domain.*;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long save(UserRequestDto userDto) {
        userRepository.save(userDto.toEntity());
        return userRepository.findByEmail(userDto.getEmail()).get().getId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundCException("해당유저를 찾을수없습니다"));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        if (ObjectUtils.isEmpty(user)) {
            throw new UserNotFoundCException("해당유저를 찾을수없습니다.");
        }
        else return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userRepository
                .findById(id).orElseThrow(()-> new UserNotFoundCException("User찾을수없음"));
        modifiedUser.setNickname(userRequestDto.getNickname());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(EmailNotFailedCException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))  //지금 받아온 password와 bean에 등록된 passwordencoder를 통해 DB에 저장된 암호화된 password일치하는지확인
            throw new PasswordFailCException("비밀번호 오류"+email);
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        String email = userSignupDto.toEntity().getEmail();
        userRepository.findByEmail(email)
            .orElseThrow(()-> new EmailExsistFailedCException("이미 해당 이메일로 계정 존재"+email));
        return userRepository.save(userSignupDto.toEntity()).getId();
    }
}