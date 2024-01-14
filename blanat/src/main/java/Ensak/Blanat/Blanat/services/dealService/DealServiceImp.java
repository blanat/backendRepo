
package Ensak.Blanat.Blanat.services.dealService;


import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.entities.Vote;
import Ensak.Blanat.Blanat.mappers.CommentMapper;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.ImagesDealRepository;
import Ensak.Blanat.Blanat.repositories.VoteRepository;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import Ensak.Blanat.Blanat.util.General;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
public class DealServiceImp implements DealServiceInterface {

    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final imagesServiceInterface imagesService;
    private final ImagesDealRepository imagesDealRepository;
    private final CommentRepository commentRepository;
    private VoteRepository voteRepository;




    @Autowired
    public DealServiceImp(DealRepository dealRepository, DealMapper dealMapper, CommentMapper commentMapper, UserMapper userMapper, imagesServiceInterface imagesService, ImagesDealRepository imagesDealRepository, CommentRepository commentRepository,VoteRepository voteRepository) {
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.imagesService = imagesService;
        this.imagesDealRepository = imagesDealRepository;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
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

    @Override
    public List<ImagesDeal> getDealImages(long dealId) {
        return null;
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

    @Override
    public List<ListDealDTO> getListDealsDTOByUserId(long id) {
        List<Deal> allDeals = (List<Deal>) dealRepository.findAllByDealCreatorId(id);
        return allDeals.stream()
                .map(this::enrichDealDTO)
                .toList();
    }


    public void updateCommentCount(long dealId) {
        // Fetch the Deal entity
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            // Update the numberComment field based on the current number of comments
            int commentCount = dealRepository.countCommentsByDealId(dealId);
            deal.setNumberOfComments(commentCount);

            System.out.println("******** - Deal ID: " + deal.getDealID() + ", Comment Count: " + deal.getNumberOfComments());

            // Save the updated Deal entity
            dealRepository.save(deal);
        }
    }



    public void incrementDeg(Long dealId, UserApp user) {

        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null && checkAndCreateVote(deal, user)) {

                log.info("Incrementing degree for dealId: {} by userId: {}", dealId, user.getId());

                // Update the degree in the deal
                deal.setDeg(deal.getDeg() + 1);
                dealRepository.save(deal);

                // Log success message
                log.info("Degree incremented successfully for dealId: {} by userId: {}", dealId, user.getId());

                // Separate method for checking and creating Vote

        } else {
            log.error("Failed to increment degree. Deal not found for dealId: {}", dealId);
            throw new IllegalStateException("Vote creation failed");
        }
    }

    public boolean checkAndCreateVote(Deal deal, UserApp user) {
        // Check if the user has already voted for this deal
        boolean hasVoted = voteRepository.existsByUserAndDeal(user, deal);
        log.info("=============hasVoted==: {}", hasVoted);

        if (!hasVoted) {
            // Create a vote entry
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setDeal(deal);
            voteRepository.save(vote);

            // Log success message
            log.info("Vote created successfully for dealId: {} by userId: {}", deal.getDealID(), user.getId());
            return true;
        } else {
            // User has already voted, handle accordingly
            log.error("User with userId: {} has already voted for dealId: {}", user.getId(), deal.getDealID());
            return false;
        }
    }


   /* public void decrementDeg(Long dealId) {
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            deal.setDeg(Math.max(0, deal.getDeg() - 1));
            dealRepository.save(deal);
            // Log success message
            log.info("Degree decremented successfully for dealId: {}", dealId);
        } else {
            // Deal not found, handle accordingly (throw exception, log, etc.)
            log.error("Failed to decrement degree. Deal not found for dealId: {}", dealId);
        }
    }
*/

    public void decrementDeg(Long dealId, UserApp user) {

        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null && checkAndCreateVote(deal, user)) {

            log.info("decrementing degree for dealId: {} by userId: {}", dealId, user.getId());

            // Update the degree in the deal
            deal.setDeg(deal.getDeg() - 1);
            dealRepository.save(deal);

            // Log success message
            log.info("Degree decremented successfully for dealId: {} by userId: {}", dealId, user.getId());

            // Separate method for checking and creating Vote

        } else {
            log.error("Failed to decrement degree. Deal not found for dealId: {}", dealId);
            throw new IllegalStateException("Vote creation failed");
        }
    }


}