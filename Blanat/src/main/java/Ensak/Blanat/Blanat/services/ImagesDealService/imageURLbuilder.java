package Ensak.Blanat.Blanat.services.ImagesDealService;

import Ensak.Blanat.Blanat.entities.ImagesDeal;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class imageURLbuilder {


    // Load the base URL from properties file or configuration
    private static final String BASE_URL = "http://localhost:8085/api/images/";

    public static String buildImageUrl(ImagesDeal image) {
        String filename = extractFilename(image.getFilePath());

        // Construct the full URL by combining the base URL and the filename
        return BASE_URL + filename;
    }

    private static String extractFilename(String filePath) {
        Path path = Paths.get(filePath);

        // Get the filename from the path
        return path.getFileName().toString();
    }

    // You can also create a method for constructing multiple URLs if needed
    public static List<String> buildImageUrls(List<ImagesDeal> imagesList) {
        List<String> imageUrls = new ArrayList<>();
        for (ImagesDeal image : imagesList) {
            imageUrls.add(buildImageUrl(image));
        }
        return imageUrls;
    }

}
