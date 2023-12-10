package Ensak.Blanat.Blanat.repositories.storageRepository;

import Ensak.Blanat.Blanat.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData,Long> {
    Optional <ImageData> findByName(String name);
}
