package Ensak.Blanat.Blanat.services.ImagesDealService;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface imagesServiceInterface {

    List<ImagesDeal> saveImagesAndPaths(List<MultipartFile> files, Deal deal) ;


    ImagesDeal saveImage(ImagesDeal image);

    List<ImagesDeal> getAllImages();

    String getFirstImageUrlForDeal(Deal deal);

    Resource loadImageAsResource(String fileName);
}
