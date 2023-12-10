package Ensak.Blanat.Blanat.services.authServices.StorageService;

import Ensak.Blanat.Blanat.entities.ImageData;
import Ensak.Blanat.Blanat.repositories.storageRepository.StorageRepository;
import Ensak.Blanat.Blanat.repositories.storageRepository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import Ensak.Blanat.Blanat.util.ImageUtils;

import java.awt.*;
import java.io.IOException;

@Service
public class StorageService {
    @Autowired
    private StorageRepository repository;
    @Autowired
    private FileDataRepository fileDataRepository;
    private final String Folderpath = "http://localhost:8080/api/storage/";
    public String uploadImage(MultipartFile file )throws IOException{
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;

    }

}
