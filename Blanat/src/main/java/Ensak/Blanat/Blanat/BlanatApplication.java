package Ensak.Blanat.Blanat;

import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.SavedDeals;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.enums.Role;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.SavedDealsRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BlanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlanatApplication.class, args);
	}


	/*@Bean
	public CommandLineRunner initDealAndCommentData(
			DealRepository dealRepository,
			CommentRepository commentRepository,
			UserRepository userRepository,
			SavedDealsRepository savedDealsRepository) {

		return args -> {
			// Create Users
			UserApp user1 = new UserApp();
			user1.setFirstName("John");
			user1.setLastName("Doe");
			user1.setEmail("john@example.com");
			user1.setPassword("password");
			user1.setRole(Role.ROLE_USER);
			user1.setCreatedAt(LocalDateTime.now());
			user1.setUpdatedAt(LocalDateTime.now());
			userRepository.save(user1);

			UserApp user2 = new UserApp();
			user2.setFirstName("Jane");
			user2.setLastName("Doe");
			user2.setEmail("jane@example.com");
			user2.setPassword("password");
			user2.setRole(Role.ROLE_USER);
			user2.setCreatedAt(LocalDateTime.now());
			user2.setUpdatedAt(LocalDateTime.now());
			userRepository.save(user2);

			// Create Deals
			Deal deal1 = new Deal();
			deal1.setTitre("Deal 1");
			deal1.setDescription("Description 1");
			deal1.setCategory(Categories.ELECTRONICS);
			deal1.setDate_debut(new Date());
			deal1.setDate_fin(new Date());
			deal1.setPrix(50.0f);
			deal1.setLocalisation("Location 1");
			deal1.setLivraison_prix(5.0f);
			deal1.setVote_up(0);
			deal1.setVote_down(0);
			deal1.setDateCreation(LocalDate.now());
			deal1.setDealCreator(user1);
			dealRepository.save(deal1);

			Deal deal2 = new Deal();
			deal2.setTitre("Deal 2");
			deal2.setDescription("Description 2");
			deal2.setCategory(Categories.FASHION);
			deal2.setDate_debut(new Date());
			deal2.setDate_fin(new Date());
			deal2.setPrix(30.0f);
			deal2.setLocalisation("Location 2");
			deal2.setLivraison_prix(3.0f);
			deal2.setVote_up(0);
			deal2.setVote_down(0);
			deal2.setDateCreation(LocalDate.now());
			deal2.setDealCreator(user2);
			dealRepository.save(deal2);

			// Create Comments
			Comment comment1 = new Comment();
			comment1.setDate(new Date());
			comment1.setContent("Comment 1 content");
			comment1.setUser(user1);
			comment1.setDeal(deal1);
			commentRepository.save(comment1);

			Comment comment2 = new Comment();
			comment2.setDate(new Date());
			comment2.setContent("Comment 2 content");
			comment2.setUser(user2);
			comment2.setDeal(deal2);
			commentRepository.save(comment2);

			// Create SavedDeals
			SavedDeals savedDeals1 = new SavedDeals();
			savedDeals1.setDate_saving(new Date());
			savedDeals1.setUser(user1);
			savedDeals1.setDeal(deal1);
			savedDealsRepository.save(savedDeals1);

			SavedDeals savedDeals2 = new SavedDeals();
			savedDeals2.setDate_saving(new Date());
			savedDeals2.setUser(user2);
			savedDeals2.setDeal(deal2);
			savedDealsRepository.save(savedDeals2);
		};
	}*/


}
