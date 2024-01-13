package Ensak.Blanat.Blanat.controllers.imageController;

import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final imagesServiceInterface  imageService;

    public ImageController(imagesServiceInterface imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/Deal/{fileName}")
    public ResponseEntity<Resource> getImageDeal(@PathVariable String fileName) {
        System.out.println("Controller image: " + fileName);
        Resource resource = imageService.loadImageAsResource(fileName,"DealReq");

        // Set the Content-Type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


    @GetMapping("/Profile/{fileName}")
    public ResponseEntity<Resource> getImageProfile(@PathVariable String fileName) {
        System.out.println("Controller image: " + fileName);
        Resource resource = imageService.loadImageAsResource(fileName,"ProfileReq");

        // Set the Content-Type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set the appropriate content type

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


    @GetMapping("/Deal/urls/{dealId}")
    public ResponseEntity<List<String>> getImageUrlsForDeal(@PathVariable long dealId) {
        List<String> imageUrls = imageService.getImagesUrlsForDeal(dealId);
        return ResponseEntity.ok(imageUrls);
    }


}