package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.DiscussionView;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionViewRepository extends JpaRepository<DiscussionView, Long> {
    DiscussionView findByDiscussionAndUser(Discussion discussion, UserApp user);
    Long countByDiscussionId(Long discussionId);
}
