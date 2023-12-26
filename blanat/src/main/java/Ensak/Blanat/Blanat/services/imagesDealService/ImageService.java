package Ensak.Blanat.Blanat.services.imagesDealService;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {
    private final ImagesDealRepository imagesDealRepository;
    private  final DealRepository dealRepository;

    public ImageService(ImagesDealRepository imagesDealRepository, DealRepository dealRepository) {
        this.imagesDealRepository = imagesDealRepository;
        this.dealRepository = dealRepository;
    }

    public ImagesDeal saveImage(MultipartFile file, final Long dealId) throws BadRequestException {
    try {
        var dealEntity = dealRepository.getOne(dealId);
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String filePath = absolutePath + "/src/main/resources/static/images/";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filePath + file.getOriginalFilename());
        Files.write(path, bytes);

        ImagesDeal image = new ImagesDeal();
        image.setName(file.getOriginalFilename());
        image.setFilePath(path.toString());
        image.setDeal(dealEntity);
        return imagesDealRepository.save(image);
    } catch (
    EntityNotFoundException e) {
        throw new BadRequestException("Deal not found");
    } catch (RuntimeException e) {
        throw new IllegalStateException("Unexpected error");
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    public ImagesDeal getImage(Long id) {
        return imagesDealRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public Resource loadImageAsResource(Long id) {
        try {
            ImagesDeal image = getImage(id); // your existing method to get image entity
            Path filePath = Paths.get(image.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<ImagesDeal> getImagesByDeal(final Deal dealEntity) {
        return imagesDealRepository.getImagesDealByDeal(dealEntity);
    }

}
