package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DealRepository extends CrudRepository<Deal, Long> {
    Deal findByTitre(String titre);
    Deal findById(long id);
    Iterable<Deal> findAllByTitre(String titre);
    long count();

    void deleteByTitre(String titre);

    boolean existsByTitre(@Param("titre") String titre);

    Iterable<Deal> findAllByOrderByDateCreationDesc();




}