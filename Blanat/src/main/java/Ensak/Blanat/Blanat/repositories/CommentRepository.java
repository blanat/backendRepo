package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
