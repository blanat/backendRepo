package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    Deal findByDealID(@Param("deal_id") long deal_id);


    List<Deal> findAllByDealCreatorId(long id);
    @Query("SELECT COUNT(c.commentId) FROM Comment c WHERE c.deal.id = :dealId")
    int countCommentsByDealId(@Param("dealId") long dealId);

}