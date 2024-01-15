package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.DiscussionView;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionViewRepository extends JpaRepository<DiscussionView, Long> {
    DiscussionView findByDiscussionAndUser(Discussion discussion, UserApp user);
    Long countByDiscussionId(Long discussionId);

    List<DiscussionView> findByDiscussion(Discussion discussion);

    void deleteByDiscussion(Discussion discussion);

    void deleteByDiscussionAndUser(Discussion discussion, UserApp user);
    List<DiscussionView> findByUser(UserApp user);
}
