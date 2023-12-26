package Ensak.Blanat.Blanat.services.discuService;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.Discussion;

import java.util.List;

public interface IDiscussionService {
    DiscussionDTO createDiscussion(DiscussionDTO discussionDTO, String email);
    List<DiscussionDTO> getAllDiscussionsInfo();
    Discussion addMessage(Long discussionId, MessageDTO messageDTO);
    Long updateViews(Long discussionId, String token);
    Discussion getDiscussionById(Long discussionId);
}
