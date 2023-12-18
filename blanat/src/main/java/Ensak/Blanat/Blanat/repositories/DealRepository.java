package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DealRepository extends CrudRepository<Deal, Long> {
    Deal findByDealID(@Param("deal_id") long deal_id);
}

