
package Ensak.Blanat.Blanat.services.imagesDealService;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
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

@Service
public class imagesServiceImp implements imagesServiceInterface {

    private final ImagesDealRepository imagesDealRepository;
    private final DealRepository dealRepository;
    private final imageURLbuilder imageUrlBuilder;


    @Autowired
    public imagesServiceImp(ImagesDealRepository imagesDealRepository, imageURLbuilder imageUrlBuilder,DealRepository dealRepository) {
        this.imagesDealRepository = imagesDealRepository;
        this.imageUrlBuilder = imageUrlBuilder;
        this.dealRepository = dealRepository;
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

        try {
            Path folderPath = Path.of("D:\\ImagesTest");

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



    public List<String> getImagesUrlsForDeal(long dealId) {
        // Retrieve the Deal entity by dealId
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            // Assuming you have a method to get images associated with a deal
            List<ImagesDeal> imagesList = imagesDealRepository.findByDeal(deal);

            if (imagesList != null) {
                // Construct URLs for the images
                return imageUrlBuilder.buildImageUrls(imagesList);
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

    private final String BASE_DEAL_IMAGE_PATH = "D:\\ImagesTest";
    private final String BASE_PROFILE_IMAGE_PATH = "D:\\ImageprofileUser";

    @Override
    public Resource loadImageAsResource(String fileName, String imageType) {
        try {
            String basePath = imageType.equals("DealReq") ? BASE_DEAL_IMAGE_PATH : BASE_PROFILE_IMAGE_PATH;

            // Use the correct basePath variable here
            Path filePath = Paths.get(basePath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                System.out.println("Successfully loaded image: " + fileName);
                return resource;
            } else {
                System.err.println("Could not read the image: " + fileName);
                throw new RuntimeException("Could not read the image: " + fileName);
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL for image: " + fileName);
            throw new RuntimeException("Malformed URL for image: " + fileName, e);
        }
    }
}