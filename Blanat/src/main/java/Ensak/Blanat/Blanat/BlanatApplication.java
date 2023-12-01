package Ensak.Blanat.Blanat;

import Ensak.Blanat.Blanat.entities.Client;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.Moderator;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class BlanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlanatApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner initDealAndCommentData(DealRepository dealRepository, CommentRepository commentRepository, UserRepository userRepository) {
//		return args -> {
//			// Create and save a Client
//			Client client = new Client();
//			client.setUsername("client1");
//			client.setPassword("password2");
//			client.setEmail("client1@example.com");
//			client.setDate_Adherence(LocalDate.now());
//			userRepository.save(client);
//
//			// Create and save a Moderator
//			Moderator moderator = new Moderator();
//			moderator.setUsername("moderator1");
//			moderator.setPassword("password1");
//			moderator.setEmail("moderator1@example.com");
//			moderator.setDate_Adherence(LocalDate.now());
//			moderator.setSuperAdmin(true);
//			userRepository.save(moderator);
//
//			// Create and save a Deal
//			Deal deal = new Deal();
//			deal.setTitre("Deal 1");
//			deal.setDescription("This is the first deal");
//			deal.setDate_debut(new Date());
//			deal.setDate_fin(new Date());
//			deal.setPrix(50.0f);
//			deal.setLocalisation("Some location");
//			deal.setLivraison_prix(5.0f);
//			deal.setVote_up(0);
//			deal.setVote_down(0);
//			deal.setDateCreation(LocalDate.now());
//			dealRepository.save(deal);
//
//			// Create and save a Comment for the Deal by the Client
//			Comment comment = new Comment();
//			comment.setDate(new Date());
//			comment.setContent("This is a comment for Deal 1");
//			comment.setUser(client);
//			comment.setDeal(deal);
//			commentRepository.save(comment);
//		};
	}


