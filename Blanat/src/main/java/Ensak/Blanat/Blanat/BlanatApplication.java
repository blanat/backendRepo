package Ensak.Blanat.Blanat;

import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class BlanatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlanatApplication.class, args);
	}


	

}
