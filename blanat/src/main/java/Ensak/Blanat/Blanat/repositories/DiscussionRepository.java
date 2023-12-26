package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<Discussion,Long> {
    Optional<Discussion> findById(Long aLong);

    @Override
    List<Discussion> findAll();
}
