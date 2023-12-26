
package Ensak.Blanat.Blanat.services.dealService;


import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.mappers.CommentMapper;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import Ensak.Blanat.Blanat.util.General;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealServiceImp implements DealServiceInterface {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final imagesServiceInterface imagesService;
    private final ImagesDealRepository imagesDealRepository;
    private final CommentRepository commentRepository;



    @Autowired
    public DealServiceImp(DealRepository dealRepository, DealMapper dealMapper, CommentMapper commentMapper, UserMapper userMapper, imagesServiceInterface imagesService, ImagesDealRepository imagesDealRepository, CommentRepository commentRepository) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.imagesService = imagesService;
        this.imagesDealRepository = imagesDealRepository;
        this.commentRepository = commentRepository;
    }

    //===============working on ==========================
    @Override
    public Deal saveDeal(Deal deal) {
        deal.setDateCreation(LocalDateTime.now());
        return dealRepository.save(deal);
    }
    //==================================================

    //Lister Deal
    @Override
    public List<ListDealDTO> getListDealsDTO() {
        List<Deal> allDeals = (List<Deal>) dealRepository.findAll();
        return allDeals.stream()
                .map(this::enrichDealDTO)
                .toList();
    }



    private ListDealDTO enrichDealDTO(Deal deal) {
        ListDealDTO listDealDTO = dealMapper.dealToListDealDTO(deal);

        // Fetch the first image of the deal
        String firstImageUrl = imagesService.getFirstImageUrlForDeal(deal);
        listDealDTO.setFirstImageUrl(firstImageUrl);

        // Calculate timePassedSinceCreation
        String timePassed = General.calculateTimePassed(deal.getDateCreation());
        listDealDTO.setTimePassedSinceCreation(timePassed);

        //new for userCreator we add the usename and imageProfile
        UserDTO dealCreatorDTO = userMapper.userToUserDTO(deal.getDealCreator());
        listDealDTO.setDealCreator(dealCreatorDTO);

        return listDealDTO;
    }



    //==================================================


/*
    public DetailDealDTO getDealDetails(long dealId) {
        Deal deal = dealRepository.findByDealID(dealId);

        if (deal == null) {
            // Handle the case where the deal with the specified ID is not found
            // You can throw an exception or return a special response based on your requirements
            return null;
        }

        // Map entities to DTOs
        List<String> imagesUrl = imageURLbuilder.buildImageUrls(imagesDealRepository.findByDeal(deal));
        UserDTO dealCreatorDTO = userMapper.userToUserDTO(deal.getDealCreator());
        List<CommentDTO> commentDTOs = commentRepository.findByDealOrderByDateAsc(deal).stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());

        // Create and return the DetailDealDTO
        return DetailDealDTO.builder()
                .imagesUrl(imagesUrl)
                .dealCreator(dealCreatorDTO)
                .comments(commentDTOs)
                .build();
    }

    @Override
    public List<ImagesDeal> getDealImages(long dealId) {
        return imagesDealRepository.findByDeal(dealRepository.findByDealID(dealId));
    }


    //==================================================


    @Override
    public List<Deal> getAllDeals() {
        return null;
    }

    @Override
    public Deal getDealById(Long dealId) {

        return dealRepository.findByDealID(dealId);


    }

    @Override
    public void updateDeal(Deal deal) {

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

    @Override
    public List<ListDealDTO> getListDealsDTOByUserId(long id) {
        List<Deal> allDeals = (List<Deal>) dealRepository.findAllByDealCreatorId(id);
        return allDeals.stream()
                .map(this::enrichDealDTO)
                .toList();
    }
}