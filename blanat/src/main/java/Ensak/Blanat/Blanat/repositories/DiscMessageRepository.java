package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.DiscMessage;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscMessageRepository extends JpaRepository<DiscMessage,Long> {
    void deleteAllByDiscussion_Id(Long discussionId);
    List<DiscMessage> findByDiscussionAndUser(Discussion discussion, UserApp user);
    void deleteByDiscussion(Discussion discussion);


    @Transactional
    @Modifying
    @Query("DELETE FROM DiscMessage  WHERE user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    List<DiscMessage> findByUserId(Long id);


}
