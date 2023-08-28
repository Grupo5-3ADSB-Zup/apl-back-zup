package school.sptech.zup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZupApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZupApplication.class, args);
	}
}
