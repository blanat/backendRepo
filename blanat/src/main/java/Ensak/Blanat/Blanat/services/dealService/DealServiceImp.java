
package Ensak.Blanat.Blanat.services.dealService;


import Ensak.Blanat.Blanat.DTOs.dealDTO.ListDealDTO;
import Ensak.Blanat.Blanat.DTOs.dealDTO.ModifyDealDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.config.notifconfig.FirebaseInitializer;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.ImagesDeal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.entities.Vote;
import Ensak.Blanat.Blanat.mappers.CommentMapper;
import Ensak.Blanat.Blanat.mappers.DealMapper;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.*;
import Ensak.Blanat.Blanat.services.imagesDealService.imagesServiceInterface;
import Ensak.Blanat.Blanat.util.General;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import Ensak.Blanat.Blanat.config.notifconfig.FirebaseInitializer.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DealServiceImp implements DealServiceInterface {
    private final UserRepository userRepository;
    private final FirebaseInitializer firebaseInitializer;
    private final DealRepository dealRepository;
    private final DealMapper dealMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final imagesServiceInterface imagesService;
    private final ImagesDealRepository imagesDealRepository;
    private final CommentRepository commentRepository;
    private VoteRepository voteRepository;


    private boolean isValidated = false;





    @Autowired
    public DealServiceImp(UserRepository userRepository, DealRepository dealRepository, DealMapper dealMapper,
                          CommentMapper commentMapper, UserMapper userMapper, imagesServiceInterface imagesService,
                          ImagesDealRepository imagesDealRepository, CommentRepository commentRepository,
                          VoteRepository voteRepository, FirebaseInitializer firebaseInitializer) {
        this.userRepository = userRepository;
        this.dealRepository = dealRepository;
        this.dealMapper = dealMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.imagesService = imagesService;
        this.imagesDealRepository = imagesDealRepository;
        this.commentRepository = commentRepository;
        this.voteRepository = voteRepository;
        this.firebaseInitializer = firebaseInitializer;
    }
    //===============working on ==========================
    @Override
    public Deal saveDeal(Deal deal) {
        deal.setDateCreation(LocalDateTime.now());
        deal.setValidated(false);
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
    public List<ListDealDTO> getValidatedDeals() {
        List<Deal> allDeals = (List<Deal>) dealRepository.findAll();
        return allDeals.stream()
                .filter(Deal::isValidated)
                .map(this::enrichDealDTO)
                .toList();
    }

    @Override
    public List<ListDealDTO> getUnvalidatedDeals() {
        List<Deal> allDeals = (List<Deal>) dealRepository.findAll();
        return allDeals.stream()
                .filter(deal -> !deal.isValidated())
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


    @Override
    public void validateDeal(Long dealId) {
        try{
            Deal deal = dealRepository.findByDealID(dealId);
            deal.setValidated(true);
            dealRepository.save(deal);


        }
        catch (Exception e){
            throw new RuntimeException("Deal not found");
        }


    }

    @Override
    public void modifyDeal(Long dealId, ModifyDealDTO modifyDealDTO) {
        try{
            Deal deal = dealRepository.findByDealID(dealId);
            deal.setTitle(modifyDealDTO.getTitle());
            deal.setDescription(modifyDealDTO.getDescription());
            deal.setCategory(modifyDealDTO.getCategory());
            deal.setPrice(modifyDealDTO.getPrice());
            deal.setNewPrice(modifyDealDTO.getNewPrice());
            deal.setLocalisation(modifyDealDTO.getLocalisation());
            deal.setDeliveryExist(modifyDealDTO.isDeliveryExist());
            deal.setDeliveryPrice(modifyDealDTO.getDeliveryPrice());
            deal.setLienDeal(modifyDealDTO.getLienDeal());
            dealRepository.save(deal);

        }
        catch (Exception e){
            throw new RuntimeException("Deal not found");
        }
    }



    @Override
    public List<ListDealDTO> getValidatedDealsByCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Gérer le cas où l'utilisateur n'est pas authentifié
            return Collections.emptyList();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String currentUsername = ((UserDetails) principal).getUsername();

            // Assurez-vous que votre méthode de recherche est insensible à la casse
            UserApp currentUser = userRepository.findByEmailIgnoreCase(currentUsername);

            if (currentUser != null) {
                List<Deal> validatedDeals = dealRepository.findByDealCreatorAndIsValidated(currentUser, true);

                return validatedDeals.stream()
                        .map(this::enrichDealDTO)
                        .toList();
            }
        }

        // Gérer le cas où l'utilisateur n'est pas trouvé dans la base de données
        return Collections.emptyList();
    }













}