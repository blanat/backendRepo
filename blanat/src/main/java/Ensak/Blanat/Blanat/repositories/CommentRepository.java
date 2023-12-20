package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByDealOrderByDateAsc(Deal deal);

}