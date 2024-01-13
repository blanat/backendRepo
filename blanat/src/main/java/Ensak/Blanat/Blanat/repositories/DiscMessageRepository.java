package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.DiscMessage;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscMessageRepository extends JpaRepository<DiscMessage,Long> {
    void deleteAllByDiscussion_Id(Long discussionId);
    List<DiscMessage> findByDiscussionAndUser(Discussion discussion, UserApp user);
    void deleteByDiscussion(Discussion discussion);

}
