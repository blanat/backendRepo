package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagesDealRepository extends JpaRepository<ImagesDeal, Long> {

    // Custom method to save an ImagesDeal entity
    ImagesDeal save(ImagesDeal image);

    // Custom method to retrieve all ImagesDeal entities
    List<ImagesDeal> findAll();

    //new
    List<ImagesDeal> findByDeal(Deal deal);

    ImagesDeal findFirstByDeal(Deal deal);


}