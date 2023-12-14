package Ensak.Blanat.Blanat.services.ImagesDealService;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class imagesServiceImp implements imagesServiceInterface {

    private final ImagesDealRepository imagesDealRepository;

    @Autowired
    public imagesServiceImp(ImagesDealRepository imagesDealRepository) {
        this.imagesDealRepository = imagesDealRepository;
    }

    @Override
    public List<ImagesDeal> saveImagesAndPaths(List<MultipartFile> files, Deal deal) {
        List<ImagesDeal> images = new ArrayList<>();

        // Save each image to the local folder and create an ImagesDeal entity with the local path
        for (MultipartFile file : files) {
            String localPath = saveImageToFileSystem(file);
            ImagesDeal image = ImagesDeal.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(localPath)
                    .deal(deal)
                    .build();
            images.add(image);
        }

        // Save the ImagesDeal entities with local paths to the database
        return imagesDealRepository.saveAll(images);
    }

    @Override
    public ImagesDeal saveImage(ImagesDeal image) {
        return imagesDealRepository.save(image);
    }

    @Override
    public List<ImagesDeal> getAllImages() {
        return imagesDealRepository.findAll();
    }

    private String saveImageToFileSystem(MultipartFile file) {
        // Implement logic to save the file to a folder and return the local path
        // You may use a unique identifier for the file name to prevent conflicts

        // Example code (replace with your logic):
        try {
            Path folderPath = Path.of("E:\\ImagesTest");

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uniqueFileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + extension;
            Path filePath = folderPath.resolve(uniqueFileName);
            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }
}
