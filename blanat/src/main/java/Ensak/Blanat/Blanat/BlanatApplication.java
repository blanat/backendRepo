package Ensak.Blanat.Blanat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("Ensak.Blanat.Blanat.repositories")
public class BlanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlanatApplication.class, args);
	}




}
