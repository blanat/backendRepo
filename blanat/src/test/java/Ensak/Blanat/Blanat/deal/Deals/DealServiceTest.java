package Ensak.Blanat.Blanat.deal.Deals;

import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.services.dealService.DealServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealServiceImp dealService;

    @Test
    public void testIncrementDeg() {
        Long dealId = 1L;
        Deal deal = new Deal();
        deal.setDeg(5); // Initial value

        when(dealRepository.findById(anyLong())).thenReturn(Optional.of(deal));

        dealService.incrementDeg(dealId);

        verify(dealRepository, times(1)).save(deal);
        assertEquals(6, deal.getDeg()); // Check if the deg value is incremented
    }

    @Test
    public void testDecrementDeg() {
        Long dealId = 1L;
        Deal deal = new Deal();
        deal.setDeg(5); // Initial value

        when(dealRepository.findById(anyLong())).thenReturn(Optional.of(deal));

        dealService.decrementDeg(dealId);

        verify(dealRepository, times(1)).save(deal);
        assertEquals(4, deal.getDeg()); // Check if the deg value is decremented
    }
}
