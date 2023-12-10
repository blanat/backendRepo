package Ensak.Blanat.Blanat.repositories.storageRepository;

import Ensak.Blanat.Blanat.entities.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository for FileData like images

public interface FileDataRepository extends JpaRepository<FileData,Integer> {

    Optional<FileData> findByName(String fileName);
}
