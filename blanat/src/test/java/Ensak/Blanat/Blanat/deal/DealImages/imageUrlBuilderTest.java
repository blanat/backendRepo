package Ensak.Blanat.Blanat.deal.DealImages;

import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class imageUrlBuilderTest {

    private imageUrlBuilder builder;

    @Value("${baseImageUrl}")
    private String baseImageUrl;

    @BeforeEach
    void setUp() {
        builder = new imageUrlBuilder();
        builder.setBaseUrl(baseImageUrl);
    }

    @Test
    void buildImageUrl() {
        // Arrange
        ImagesDeal image = new ImagesDeal();
        image.setFilePath("C:\\path\\to\\file.jpg");

        // Act
        String imageUrl = builder.buildImageUrl(image);

        // Assert
        assertEquals(baseImageUrl + "Deal/file.jpg", imageUrl);
    }

    @Test
    void buildProfileImageUrl() {
        // Arrange
        String profileImage = "C:\\path\\to\\profile.jpg";

        // Act
        String profileImageUrl = builder.buildProfileImageUrl(profileImage);

        // Assert
        assertEquals(baseImageUrl + "Profile/profile.jpg", profileImageUrl);
    }

    @Test
    void extractFilename() {
        // Arrange
        String filePath = "C:\\path\\to\\file.jpg";

        // Act
        String filename = builder.extractFilename(filePath);

        // Assert
        assertEquals("file.jpg", filename);
    }

    @Test
    void buildImageUrls() {
        // Arrange
        ImagesDeal image1 = new ImagesDeal();
        image1.setFilePath("C:\\path\\to\\file1.jpg");

        ImagesDeal image2 = new ImagesDeal();
        image2.setFilePath("C:\\path\\to\\file2.jpg");

        List<ImagesDeal> imagesList = Arrays.asList(image1, image2);

        // Act
        List<String> imageUrls = builder.buildImageUrls(imagesList);

        // Assert
        assertEquals(Arrays.asList(
                baseImageUrl + "Deal/file1.jpg",
                baseImageUrl + "Deal/file2.jpg"), imageUrls);
    }


    @Test
    void buildImageUrls_EmptyList() {
        // Arrange
        List<ImagesDeal> emptyImageList = new ArrayList<>();

        // Act
        List<String> resultUrls = imageUrlBuilder.buildImageUrls(emptyImageList);

        // Assert
        assertEquals(0, resultUrls.size(), "The resulting list should be empty for an empty input list.");
    }
}
