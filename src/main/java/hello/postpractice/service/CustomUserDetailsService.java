package hello.postpractice.service;

import hello.postpractice.advice.exception.EmailNotFailedCException;
import hello.postpractice.advice.exception.UserNotFoundCException;
import hello.postpractice.domain.User;
import hello.postpractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UserNotFoundCException {
        return userRepository.findById(Long.parseLong(userPk)).orElseThrow(UserNotFoundCException::new);
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFailedCException::new);
    }
}
