package Ensak.Blanat.Blanat.services.DealServices;

import Ensak.Blanat.Blanat.DTOs.dealDTO.DealDTO;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DealService {

    private final DealRepository dealRepository;
    private final UserService userService;

    @Autowired
    public DealService(DealRepository dealService, UserService userService) {
        this.dealRepository = dealService;
        this.userService = userService;
    }

    public Deal createDeal(DealDTO dealDTO) {
        // Récupérer l'utilisateur connecté
        UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Créer une nouvelle discussion
        Deal deal = Deal.builder()
                .titre(dealDTO.getTitre())
                .description(dealDTO.getDescription())
                .prix(dealDTO.getPrix())
                .description(dealDTO.getDescription())
                .nbre_comment(0)
                .category(dealDTO.getCategories())
                .dealCreator(currentUser)
                .build();

        // Enregistrer la discussion dans la base de données
        return dealRepository.save(deal);
    }
}
