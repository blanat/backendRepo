package Ensak.Blanat.Blanat.controllers.dealsController;

import Ensak.Blanat.Blanat.DTOs.dealDTO.CreateDealDTO;
import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
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
    private final imagesServiceInterface imageService;

    private final UserService userService;
    private final DealMapper dealMapper;

    @Autowired
    public DealController(DealServiceInterface dealService, UserService userService, DealMapper dealMapper,imagesServiceInterface imageService) {
        this.dealService = dealService;
        this.userService = userService;
        this.dealMapper = dealMapper;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<String> createDeal(
            @RequestHeader("Authorization") String token,
            @RequestPart("deal") CreateDealDTO dealDTO,
            @RequestPart("images") List<MultipartFile> images) {
        try {
            //token information
            UserApp user = userService.getUserFromToken(token);
            // Convert the CreateDealDTO to a Deal entity using DealMapper
            Deal dealEntity = dealMapper.createDealDTOToDeal(dealDTO);
            dealEntity.setDealCreator(user);
            Deal newDeal = dealService.saveDeal(dealEntity);

            imageService.saveImagesAndPaths(images, newDeal);

            return new ResponseEntity<>("Deal data received successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing deal data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping
    public ResponseEntity<List<ListDealDTO>> getListDealsDTO() {
        try {
            List<ListDealDTO> listDealDTOs = dealService.getListDealsDTO();
            return new ResponseEntity<>(listDealDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // this method is used to get the details of a specific deal
    @GetMapping("/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable("id") long id) {
        try {
            Deal deal = dealService.getDealById(id);
            return new ResponseEntity<>(deal, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // this method is used to get the list of deals of a specific user
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ListDealDTO>> getListDealsDTOByUserId(@PathVariable("id") long id) {
        try {
            List<ListDealDTO> listDealDTOs = dealService.getListDealsDTOByUserId(id);
            return new ResponseEntity<>(listDealDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    //==================================

/*

    @GetMapping("details/{dealId}")
    public ResponseEntity<DetailDealDTO> getDealDetails(@PathVariable Long dealId) {
        DetailDealDTO detailDealDTO = dealService.getDealDetails(dealId);

        if (detailDealDTO == null) {
            // Handle deal not found
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detailDealDTO);
    }
*/




    @GetMapping("/{dealId}/images")
    public ResponseEntity<List<ImagesDeal>> getAllDealImages(@PathVariable Long dealId) {
        List<ImagesDeal> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}
