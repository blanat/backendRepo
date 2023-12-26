package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.DiscMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscMessageRepository extends JpaRepository<DiscMessage,Long> {
    void deleteAllByDiscussion_Id(Long discussionId);
}
