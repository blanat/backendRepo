package Ensak.Blanat.Blanat.services.discuService;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.DiscMessage;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.exeptions.AuthenticationException;
import Ensak.Blanat.Blanat.repositories.DiscMessageRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscMessageService {

    private final DiscMessageRepository discMessageRepository;
    private final DiscussionRepository discussionRepository;


    @Autowired
    public DiscMessageService(DiscMessageRepository discMessageRepository,
                              DiscussionRepository discussionRepository) {
        this.discMessageRepository = discMessageRepository;
        this.discussionRepository = discussionRepository;
    }
    @Transactional
    public MessageDTO addMessage(Long discussionId, MessageDTO messageDTO) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp currentUser = (UserApp) authentication.getPrincipal();

        DiscMessage newMessage = DiscMessage.builder()
                .discussion(discussion)
                .user(currentUser)
                .content(messageDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        // Sauvegarder le nouveau message
        DiscMessage savedMessage = discMessageRepository.save(newMessage);

        // Create a response DTO with the required fields
        MessageDTO responseDTO = new MessageDTO();
        responseDTO.setContent(savedMessage.getContent());
        responseDTO.setCreatedAt(savedMessage.getCreatedAt());
        responseDTO.setUserName(savedMessage.getUser().getUserName());

        // Return the DTO as the response
        return responseDTO;
    }





    private DiscussionDTO convertToDTO(Discussion discussion) {
        // Implémentez la logique de conversion de Discussion vers DiscussionDTO ici
        // Assurez-vous de définir correctement le créateurId et d'autres champs si nécessaire
        return new DiscussionDTO(discussion.getId(), discussion.getTitre(),discussion.getCategories(),discussion.getCreateur().getUserName(),discussion.getNbrvue(),discussion.getCreateur().getProfileFilePath());
    }


    public Discussion getDiscussionById(Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new EntityNotFoundException("Discussion not found"));


        return discussion;
    }



    public List<MessageDTO> getCommentsByDiscussionId(Long discussionId) {

        Discussion discussion = getDiscussionById(discussionId);
        List<DiscMessage> discMessages = discussion.getDiscMessage();

        return discMessages.stream()
                .map(this::mapToCommentDTO)
                .collect(Collectors.toList());
    }

    //mapping between messagedto and discmessage
    private MessageDTO mapToCommentDTO(DiscMessage discMessage) {
        MessageDTO commentDTO = new MessageDTO();
        commentDTO.setContent(discMessage.getContent());
        commentDTO.setCreatedAt(discMessage.getCreatedAt());
        commentDTO.setUserName(discMessage.getUser().getUserName());
        commentDTO.setProfileImageUrl(discMessage.getUser().getProfileFilePath());
        return commentDTO;
    }


}
