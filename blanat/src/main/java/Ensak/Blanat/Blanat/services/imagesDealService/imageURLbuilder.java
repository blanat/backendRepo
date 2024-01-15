package Ensak.Blanat.Blanat.services.imagesDealService;

import Ensak.Blanat.Blanat.entities.ImagesDeal;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class imageURLbuilder {


    // Load the base URL from properties file or configuration
    private static final String BASE_URL = "http://192.168.1.104:8085/api/images/";


    public static String buildImageUrl(ImagesDeal image) {
        String filename = extractFilename(image.getFilePath());
        return BASE_URL + "Deal/" + filename;
    }

    public static String buildProfileImageUrl(String image) {
        String filename = extractFilename(image);
        return BASE_URL + "Profile/" + filename;
    }


    public static String extractFilename(String filePath) {
        // Split the file path to get the filename
        String[] parts = filePath.split("\\\\");
        return parts[parts.length - 1]; // Get the last part as the filename
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