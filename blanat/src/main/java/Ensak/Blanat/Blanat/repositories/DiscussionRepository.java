package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<Discussion,Long> {
    Optional<Discussion> findById(Long aLong);
    List<Discussion> findByCreateur(UserApp createur);

    @Override
    List<Discussion> findAll();

    List<Discussion> findAllByViewersContaining(UserApp user);

    List<Discussion> findBySave(int save);

    List<Discussion>findByViewersAndSave(UserApp user, int save);

    Long countByCreateur(UserApp currentUser);
}
