package Ensak.Blanat.Blanat.controllers.DiscController;
import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.DiscMessage;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.services.discuService.DiscMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscMessageController {

    private final DiscMessageService discMessageService;

    @Autowired
    public DiscMessageController(DiscMessageService discMessageService) {
        this.discMessageService = discMessageService;
    }

    @PostMapping("/{discussionId}/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageDTO> addMessage(@PathVariable Long discussionId,
                                                  @RequestBody MessageDTO messageDTO) {
        MessageDTO newMessage = discMessageService.addMessage(discussionId, messageDTO);
        return ResponseEntity.ok(newMessage);
    }



    @GetMapping("/{discussionId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDTO>> getCommentsByDiscussionId(@PathVariable Long discussionId) {
        List<MessageDTO> comments = discMessageService.getCommentsByDiscussionId(discussionId);
        return ResponseEntity.ok(comments);
    }
















}
