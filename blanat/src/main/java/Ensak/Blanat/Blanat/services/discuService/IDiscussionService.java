package Ensak.Blanat.Blanat.services.discuService;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;

import java.util.List;

public interface IDiscussionService {
    DiscussionDTO createDiscussion(DiscussionDTO discussionDTO, String email);
    List<DiscussionDTO> getAllDiscussionsInfo();
    Discussion addMessage(Long discussionId, MessageDTO messageDTO);
    Long updateViews(Long discussionId, String token);
    Discussion getDiscussionById(Long discussionId);

    void deleteDiscussionAndMessages(Long discussionId);

    int updateSave(Long discussionId);

    List<Discussion> getSavedDiscussionsByUser(UserApp user);
}
