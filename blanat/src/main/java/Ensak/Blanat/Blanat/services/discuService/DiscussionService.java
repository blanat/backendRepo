package Ensak.Blanat.Blanat.services.discuService;
import Ensak.Blanat.Blanat.DTOs.dealDTO.DealDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final UserService userService;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserService userService) {
        this.discussionRepository = discussionRepository;
        this.userService = userService;
    }

    public Discussion createDiscussion(DiscussionDTO DiscussionDTO) {
        // Récupérer l'utilisateur connecté
        UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Créer une nouvelle discussion
        Discussion discussion = Discussion.builder()
                .titre(DiscussionDTO.getTitre())
                .description(DiscussionDTO.getDescription())
                .nbrvue(0)
                .categories(DiscussionDTO.getCategories())
                .createur(currentUser)
                .build();


        // Enregistrer la discussion dans la base de données
        return discussionRepository.save(discussion);
    }
}
