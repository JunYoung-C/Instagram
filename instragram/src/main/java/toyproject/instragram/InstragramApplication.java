package toyproject.instragram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InstragramApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstragramApplication.class, args);
	}
	//TODO 예외 테스트 코드 보완
}
