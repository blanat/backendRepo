package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long>  {
    boolean existsByUserAndDeal(UserApp user, Deal deal);
    List<Vote> findByUserId(Long id);
}
