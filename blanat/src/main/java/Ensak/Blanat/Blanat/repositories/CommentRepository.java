package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDealOrderByDateAsc(Deal deal);


    Long countByuser(UserApp currentUser);

   // long countByDiscussion(Discussion discussion);
}