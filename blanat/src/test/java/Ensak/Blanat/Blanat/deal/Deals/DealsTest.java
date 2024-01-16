package Ensak.Blanat.Blanat.deal.Deals;

import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import Ensak.Blanat.Blanat.services.dealService.DealServiceImp;
import Ensak.Blanat.Blanat.util.General;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealsTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private ImagesDealRepository imagesDealRepository;

    @InjectMocks
    private DealServiceImp dealService;

    @Test
    public void saveDeal_ShouldSaveDealWithCurrentTimestamp() {
        // Arrange
        Deal dealToSave = new Deal();
        when(dealRepository.save(any())).thenReturn(dealToSave);

        // Act
        Deal savedDeal = dealService.saveDeal(new Deal());

        // Assert
        verify(dealRepository).save(any(Deal.class));
        assertNotNull(savedDeal.getDateCreation()); // Assuming you have a getter for dateCreation
    }

    @Test
    public void getListDealsDTO_ShouldReturnListDealDTO() {
        // Arrange
        when(dealRepository.findAll()).thenReturn(Collections.singletonList(new Deal()));

        // Act
        List<ListDealDTO> listDealDTOs = dealService.getListDealsDTO();

        // Assert
        verify(dealRepository).findAll();
        assertFalse(listDealDTOs.isEmpty());
        // Add more assertions based on your logic
    }

    // Add more tests for other methods...

    // Example of a utility method for setting a LocalDateTime in the past
    private LocalDateTime getPastLocalDateTime() {
        return LocalDateTime.now().minusDays(1);
    }
}
