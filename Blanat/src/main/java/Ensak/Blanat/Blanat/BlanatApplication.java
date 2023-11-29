package Ensak.Blanat.Blanat;

import Ensak.Blanat.Blanat.entities.Client;
import Ensak.Blanat.Blanat.entities.Moderator;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BlanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlanatApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {


			// Create and save a Client
			Client client = new Client();
			client.setUsername("client1");
			client.setPassword("password2");
			client.setEmail("client1@example.com");
			client.setDate_Adherence(LocalDate.now());
			userRepository.save(client);

			// Create and save a Moderator
			Moderator moderator = new Moderator();
			moderator.setUsername("moderator1");
			moderator.setPassword("password1");
			moderator.setEmail("moderator1@example.com");
			moderator.setDate_Adherence(LocalDate.now());
			moderator.setSuperAdmin(true);
			userRepository.save(moderator);
		};
	}
}
