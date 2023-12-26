package Ensak.Blanat.Blanat.services.discuService;
import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.DiscMessage;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.DiscussionView;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.exeptions.*;
import Ensak.Blanat.Blanat.repositories.DiscMessageRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionViewRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.services.imagesDealService.imageURLbuilder;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscussionServiceImpl implements IDiscussionService{
    @Autowired
    private DiscussionViewRepository discussionViewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private final imageURLbuilder imageBuilder;

    private final DiscMessageRepository discMessageRepository;



    private final JwtService jwtTokenService;
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;

    @Autowired
    public DiscussionServiceImpl(DiscussionViewRepository discussionViewRepository, UserService userService, DiscMessageRepository discMessageRepository, JwtService jwtTokenService, DiscussionRepository discussionRepository, UserRepository userRepository, imageURLbuilder imageBuilder) {
        this.discussionViewRepository = discussionViewRepository;
        this.userService = userService;
        this.discMessageRepository = discMessageRepository;
        this.jwtTokenService = jwtTokenService;
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
        this.imageBuilder = imageBuilder;
    }



    public DiscussionDTO createDiscussion(DiscussionDTO discussionDTO, String email) {
        Optional<UserApp> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserApp createur = userOptional.get();

            Discussion discussion = Discussion.builder()
                    .id(discussionDTO.getId())
                    .titre(discussionDTO.getTitre())
                    .description(discussionDTO.getDescription())
                    .categories(discussionDTO.getCategories())
                    .createur(createur)
                    .build();

            Discussion savedDiscussion = discussionRepository.save(discussion);

            // Créer et retourner un objet DiscussionDTO avec les détails requis
            DiscussionDTO responseDTO = new DiscussionDTO();
            responseDTO.setId(savedDiscussion.getId());
            responseDTO.setTitre(savedDiscussion.getTitre());
            responseDTO.setDescription(savedDiscussion.getDescription());
            responseDTO.setCategories(savedDiscussion.getCategories());
            responseDTO.setCreateurUsername(createur.getUserName());

            return responseDTO;
        } else {
            throw new UserNotFoundException("User not found for email: " + email);
        }
    }



    @Transactional
    public Discussion addMessage(Long discussionId, MessageDTO messageDTO) {

        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationException("User not authenticated");
        }
        UserApp currentUser = (UserApp) authentication.getPrincipal();

        DiscMessage comment = DiscMessage.builder()
                .discussion(discussion)
                .user(currentUser)
                .content(messageDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        discussion.getDiscMessage().add(comment);
        return discussionRepository.save(discussion);
    }




    public List<DiscussionDTO> getAllDiscussionsInfo() {
        try {
            List<Discussion> discussions = discussionRepository.findAll();

            return discussions.stream()
                    .map(discussion -> new DiscussionDTO(
                            discussion.getId(),
                            discussion.getTitre(),
                            discussion.getCategories(),
                            discussion.getCreateur().getUserName(),
                            discussion.getNbrvue(),
                            // Utilisation de la méthode buildProfileImageUrl pour récupérer l'URL de l'image de profil
                            imageBuilder.buildProfileImageUrl(discussion.getCreateur().getProfileFilePath())
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiscussionServiceException("Une erreur s'est produite lors de la récupération des discussions.");
        }
    }

    @Transactional
    public Long updateViews(Long discussionId, String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7); // Extrait le JWT sans "Bearer "
                String email = jwtTokenService.ExtractUserName(jwt);

                if (email != null) {
                    UserApp currentUser = userService.getUserByUsername(email);

                    if (currentUser != null) {
                        Optional<Discussion> discussionOptional = discussionRepository.findById(discussionId);
                        if (discussionOptional.isEmpty()) {
                            throw new DiscussionNotFoundException("Discussion not found");
                        }

                        Discussion discussion = discussionOptional.get();

                        DiscussionView existingView = discussionViewRepository.findByDiscussionAndUser(discussion, currentUser);
                        if (existingView == null) {
                            discussion.setNbrvue(discussion.getNbrvue() + 1);
                            discussionRepository.save(discussion);

                            DiscussionView newView = new DiscussionView();
                            newView.setDiscussion(discussion);
                            newView.setUser(currentUser);
                            discussionViewRepository.save(newView);
                        }

                        return (long) discussion.getNbrvue();
                    }
                }
            }
            throw new UnauthorizedException("Unauthorized access");
        } catch (DiscussionNotFoundException ex) {
            ex.printStackTrace(); // Logger l'erreur
            throw ex;
        } catch (UnauthorizedException | MalformedJwtException ex) {
            ex.printStackTrace(); // Logger l'erreur
            throw ex;
        } catch (Exception e) {
            e.printStackTrace(); // Logger l'erreur
            throw new RuntimeException("Error updating views for discussion: " + discussionId, e);
        }
    }




    // Méthode pour obtenir une discussion par son ID
    public Discussion getDiscussionById(Long discussionId) {
        Optional<Discussion> discussionOptional = discussionRepository.findById(discussionId);
        return discussionOptional.orElseThrow(() -> new EntityNotFoundException("Discussion not found"));
    }


    public List<Discussion> getDiscussionsCreatedByCurrentUser() {
        // Récupérer l'utilisateur actuellement authentifié
        UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Récupérer toutes les discussions créées par l'utilisateur connecté
        return discussionRepository.findByCreateur(currentUser);
    }

    @Transactional
    public void deleteDiscussionAndComments(Long discussionId) {
        Optional<Discussion> discussionOptional = discussionRepository.findById(discussionId);
        discussionOptional.ifPresent(discussion -> {
            List<DiscMessage> comments = discussion.getDiscMessage();
            discMessageRepository.deleteAll(comments);

            List<DiscussionView> discussionViews = discussionViewRepository.findByDiscussion(discussion);
            discussionViewRepository.deleteAll(discussionViews);

            discussionRepository.delete(discussion);
        });
    }
}
