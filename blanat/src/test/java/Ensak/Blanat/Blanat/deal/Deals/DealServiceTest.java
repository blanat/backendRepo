package Ensak.Blanat.Blanat.deal.Deals;

import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.enums.Role;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.services.dealService.DealServiceImp;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealMapper dealMapper;

    @Mock
    private imagesServiceInterface imagesService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private DealServiceImp dealService;

    public DealServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getListDealsDTO_NoDeals() {
        // Arrange
        when(dealRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ListDealDTO> result = dealService.getListDealsDTO();

        // Assert
        assertTrue(result.isEmpty());
        verify(dealRepository, times(1)).findAll();
    }

    @Test
    void getListDealsDTO_WithDeals() {
        // Arrange
        Deal deal = createSampleDeal();
        List<Deal> deals = Collections.singletonList(deal);
        when(dealRepository.findAll()).thenReturn(deals);

        // Mock the behavior of other dependencies if needed

        // Act
        List<ListDealDTO> result = dealService.getListDealsDTO();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        // Add more assertions based on your expectations
        verify(dealRepository, times(1)).findAll();
        // Verify interactions with other dependencies
    }

    // Add more test cases as needed

    private Deal createSampleDeal() {
        Deal deal = new Deal();
        deal.setTitle("Sample Deal");
        deal.setDescription("This is a sample deal description.");
        deal.setPrice(50);
        deal.setNewPrice(20);
        deal.setCategory(Categories.FASHION);
        deal.setDateCreation(LocalDateTime.now());
        deal.setDateDebut(new Date());
        deal.setDateFin(new Date());
        deal.setLienDeal("https://example.com");
        deal.setLocalisation("Sample Location");
        deal.setDealCreator(createSampleUser());
        return deal;
    }

    private UserApp createSampleUser() {
        return UserApp.builder()
                .userName("SampleUser")
                .email("sample.user@example.com")
                .role(Role.ROLE_USER)
                .build();
    }
}
