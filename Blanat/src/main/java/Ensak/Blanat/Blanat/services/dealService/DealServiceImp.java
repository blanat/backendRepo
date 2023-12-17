package Ensak.Blanat.Blanat.services.dealService;

import Ensak.Blanat.Blanat.DTOs.DealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.services.ImagesDealService.imagesServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealServiceImp implements DealServiceInterface {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final imagesServiceInterface imagesService;

    @Autowired
    public DealServiceImp(DealRepository dealRepository, DealMapper dealMapper, imagesServiceInterface imagesService) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.imagesService = imagesService;
    }

    //===============working on ==========================
    @Override
    public Deal saveDeal(Deal deal) {
        deal.setDateCreation(LocalDateTime.now());
        Deal createdDeal = dealRepository.save(deal);
        return createdDeal;
    }
    //==================================================

    //Lister Deal
    @Override
    public List<ListDealDTO> getListDealsDTO() {
        List<Deal> allDeals = dealRepository.findAll();
        return allDeals.stream()
                .map(this::enrichDealDTO)
                .toList();
    }

    private ListDealDTO enrichDealDTO(Deal deal) {
        ListDealDTO listDealDTO = dealMapper.dealToListDealDTO(deal);

        // Fetch the first image of the deal
        String firstImageUrl = imagesService.getFirstImageUrlForDeal(deal);
        listDealDTO.setFirstImageURL(firstImageUrl);

        // Calculate timePassedSinceCreation
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime creationTime = deal.getDateCreation();
        Duration duration = Duration.between(creationTime, now);

        long hours = duration.toHours();
        if (hours <= 23) {
            listDealDTO.setTimePassedSinceCreation(hours + " hours");
        } else {
            long days = duration.toDays();
            listDealDTO.setTimePassedSinceCreation(days + " days");
        }

        return listDealDTO;
    }
    //==================================================

    @Override
    public List<Deal> getAllDeals() {
        return null;
    }

    @Override
    public Deal getDealById(Long dealId) {
        return null;
    }

    @Override
    public void updateDeal(Deal deal) {
        // Implement update logic
    }

    @Override
    public void deleteDeal(Long dealId) {
        // Implement delete logic
    }

    @Override
    public void getLatestDeals() {
        // Implement logic to get the latest deals
    }

    @Override
    public void getDealsSortedByVoteup(Long dealId) {
        // Implement logic to get deals sorted by vote up
    }

    @Override
    public void getActiveDeals(Long dealId) {
        // Implement logic to get active deals
    }
}
