package hello.postpractice.repository;
import java.util.Optional;

import hello.postpractice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long > {
    Optional<User> findByEmail(String email);
}