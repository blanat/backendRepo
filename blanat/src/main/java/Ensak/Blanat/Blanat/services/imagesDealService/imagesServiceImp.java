
package Ensak.Blanat.Blanat.services.imagesDealService;

import Ensak.Blanat.Blanat.config.appconfig.AppConfiguration;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.exeptions.FileReadException;
import Ensak.Blanat.Blanat.exeptions.FileStorageException;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class imagesServiceImp implements imagesServiceInterface {

    private final ImagesDealRepository imagesDealRepository;
    private final DealRepository dealRepository;
    private final Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder imageUrlBuilder;
    private final AppConfiguration appConfiguration;


    @Autowired
    public imagesServiceImp(ImagesDealRepository imagesDealRepository, Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder imageUrlBuilder, DealRepository dealRepository, AppConfiguration appConfiguration) {
        this.imagesDealRepository = imagesDealRepository;
        this.imageUrlBuilder = imageUrlBuilder;
        this.dealRepository = dealRepository;
        this.appConfiguration = appConfiguration;
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

    public String saveImageToFileSystem(MultipartFile file) {
        try {
            if (file == null) {
                throw new FileStorageException("File cannot be null");
            }

            String imageStoragePath = appConfiguration.getDealImagePath();
            Path folderPath = Path.of(imageStoragePath);

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new FileStorageException("Original filename cannot be null");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uniqueFileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID() + extension;
            Path filePath = folderPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new FileStorageException("Failed to save file", e);
        }
    }



    @Override
    public String getFirstImageUrlForDeal(Deal deal) {
        // Assuming you have a method to get the first image associated with a deal
        ImagesDeal firstImage = imagesDealRepository.findFirstByDeal(deal);
        if (firstImage != null) {
            // Construct URL for the first image
            return imageUrlBuilder.buildImageUrl(firstImage);
        } else {
            // Handle the case when there are no images associated with the deal
            return null;
        }
    }



    @Override
    public List<String> getImagesUrlsForDeal(long dealId) {
        // Retrieve the Deal entity by dealId
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            // Assuming you have a method to get images associated with a deal
            List<ImagesDeal> imagesList = imagesDealRepository.findByDeal(deal);

            if (imagesList != null) {
                // Construct URLs for the images using the class name
                return Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder.buildImageUrls(imagesList);
            } else {
                // Handle the case when there are no images associated with the deal
                return new ArrayList<>();
            }
        } else {
            // Handle the case when the deal with the given ID is not found
            return new ArrayList<>();
        }
    }



    //==================================================================================
    //==================================================================================

    private static final Logger logger = LoggerFactory.getLogger(imagesServiceImp.class);

    public Resource loadImageAsResource(String fileName, String imageType) {
        try {
            String basePath = imageType.equals("DealReq") ? appConfiguration.getDealImagePath() : appConfiguration.getProfileImagePath();

            // Use the correct basePath variable here
            Path filePath = Paths.get(basePath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                logger.info("Successfully loaded image: {}", fileName);
                return resource;
            } else {
                logger.error("Could not read the image: {}", fileName);
                throw new FileReadException("Could not read the image: " + fileName);
            }
        } catch (MalformedURLException e) {
            logger.error("Malformed URL for image: {}", fileName, e);
            throw new FileReadException("Malformed URL for image: " + fileName, e);
        }
    }
}