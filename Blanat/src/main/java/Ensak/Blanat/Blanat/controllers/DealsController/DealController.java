package Ensak.Blanat.Blanat.controllers.DealsController;

import Ensak.Blanat.Blanat.DTOs.DealDTO.CreateDealDTO;
import Ensak.Blanat.Blanat.DTOs.DealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.ImagesDealService.imagesServiceInterface;
import Ensak.Blanat.Blanat.services.authServices.AuthenticationService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.services.dealService.DealServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealServiceInterface dealService;
    private final imagesServiceInterface iamgeService;

    private final UserService userService;
    private final DealMapper dealMapper;

    @Autowired
    public DealController(DealServiceInterface dealService, UserService userService, DealMapper dealMapper,imagesServiceInterface iamgeService) {
        this.dealService = dealService;
        this.userService = userService;
        this.dealMapper = dealMapper;
        this.iamgeService = iamgeService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDeal(
            @RequestHeader("Authorization") String token,
            @RequestPart("deal") CreateDealDTO dealDTO,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            //token info
            System.out.println("Received token :" + token);

            UserApp user = userService.getUserFromToken(token);

            // Convert the CreateDealDTO to a Deal entity using DealMapper
            Deal dealEntity = dealMapper.createDealDTOToDeal(dealDTO);

            dealEntity.setDealCreator(user);

            Deal CDeal = dealService.saveDeal(dealEntity);

            iamgeService.saveImagesAndPaths(images, CDeal);

            // Print out the received deal data
            System.out.println("Received Deal Data:");
            System.out.println("Title: " + dealEntity.getTitle());
            System.out.println("Description: " + dealEntity.getDescription());

            // Print out the received image data
            System.out.println("Received Images:");
            for (MultipartFile image : images) {
                System.out.println("Image Name: " + image.getOriginalFilename());
                System.out.println("Image Size: " + image.getSize() + " bytes");
                // You can add more details about each image if needed
            }



            // Instead of saving, just return a success response
            return new ResponseEntity<>("Deal data received successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing deal data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/listDeals")
    public ResponseEntity<List<ListDealDTO>> getListDealsDTO() {
        try {
            List<ListDealDTO> listDealDTOs = dealService.getListDealsDTO();
            return ResponseEntity.ok(listDealDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}

