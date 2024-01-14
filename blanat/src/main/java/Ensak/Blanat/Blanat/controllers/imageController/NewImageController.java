package Ensak.Blanat.Blanat.controllers.imageController;

import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.services.imagesDealService.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/images")
public class NewImageController {
    private final ImageService imageService;

    @Autowired
    public NewImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImagesDeal> uploadImage(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("dealId") long dealId)
            throws IOException {
        ImagesDeal image = imageService.saveImage(file, dealId);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagesDeal> getImage(@PathVariable Long id) {
        ImagesDeal image = imageService.getImage(id);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getImageFile(@PathVariable Long id) {
        Resource imageFile = imageService.loadImageAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + imageFile.getFilename() + "\"")
                .body(imageFile);
    }

}
