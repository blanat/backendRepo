package Ensak.Blanat.Blanat.deal.Deals;

import Ensak.Blanat.Blanat.DTOs.dealDTO.CreateDealDTO;
import Ensak.Blanat.Blanat.controllers.dealsController.DealController;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.enums.Role;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.services.dealService.DealServiceInterface;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateDealTest {

    @Mock
    private DealServiceInterface dealService;

    @Mock
    private imagesServiceInterface imageService;

    @Mock
    private UserService userService;

    @Mock
    private DealMapper dealMapper;

    @InjectMocks
    private DealController dealController;

    public CreateDealTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDeal_Success() {
        // Arrange
        UserApp user = UserApp.builder()
                .userName("Bensar")
                .email("Bensar@gmail.com")
                .password("password")
                .role(Role.ROLE_USER)
                .build();

        CreateDealDTO createDealDTO = new CreateDealDTO();
        createDealDTO.setTitle("Sample Deal");
        createDealDTO.setDescription("This is a sample deal description.");
        createDealDTO.setPrice(50);
        createDealDTO.setNewPrice(20);
        createDealDTO.setCategory(Categories.FASHION);
        createDealDTO.setDateDebut(new Date());
        createDealDTO.setDateFin(new Date());
        createDealDTO.setLienDeal("Https://lien.com");
        createDealDTO.setLocalisation("Rabat agdal");




        Deal dealEntity = new Deal();  // Create a sample Deal entity
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "image content".getBytes());

        // Mock the behavior of userService.getUserFromToken
        when(userService.getUserFromToken(any(String.class))).thenReturn(user);

        // Mock the behavior of dealMapper.createDealDTOToDeal
        when(dealMapper.createDealDTOToDeal(any(CreateDealDTO.class))).thenReturn(dealEntity);

        // Mock the behavior of dealService.saveDeal
        when(dealService.saveDeal(any(Deal.class))).thenReturn(dealEntity);

        // Mock the behavior of imageService.saveImagesAndPaths
        when(imageService.saveImagesAndPaths(anyList(), any(Deal.class))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<String> response = dealController.createDeal("fake-token", createDealDTO, Collections.singletonList(imageFile));

        // Assert
        assertEquals("Deal data received successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());

        // Verify interactions
        verify(userService, times(1)).getUserFromToken(any(String.class));
        verify(dealMapper, times(1)).createDealDTOToDeal(any(CreateDealDTO.class));
        verify(dealService, times(1)).saveDeal(any(Deal.class));
        verify(imageService, times(1)).saveImagesAndPaths(anyList(), any(Deal.class));
    }

}
