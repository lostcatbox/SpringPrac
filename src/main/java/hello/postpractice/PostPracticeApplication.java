package hello.postpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy //AspectJ 를 위한 추가
public class PostPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostPracticeApplication.class, args);
	}

}
