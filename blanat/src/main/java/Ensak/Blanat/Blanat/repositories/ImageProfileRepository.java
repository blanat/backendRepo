package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.ImageProfile;
import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ImageProfileRepository extends JpaRepository<ImageProfile, Long> {

    void deleteByUserApp(UserApp user);

    ImageProfile findByUserApp(UserApp user);
}
