package Ensak.Blanat.Blanat.controllers.DiscController;

import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.exeptions.DiscussionNotFoundException;
import Ensak.Blanat.Blanat.exeptions.UnauthorizedException;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.services.discuService.DiscussionServiceImpl;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    private final DiscussionServiceImpl discussionService;



    // Injecter les services nécessaires dans le constructeur


    public DiscussionController(DiscussionServiceImpl discussionService) {
        this.discussionService = discussionService;
    }


    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createDiscussion(@RequestBody DiscussionDTO discussionDTO, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the authenticated user
        UserApp user = (UserApp) userDetails;

        // Create the discussion
        DiscussionDTO responseDTO = discussionService.createDiscussion(discussionDTO, user.getUsername());

        return ResponseEntity.ok().body(responseDTO);
    }




    @PostMapping("/{discussionId}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Discussion> addMessageToDiscussion(
            @PathVariable("discussionId") Long discussionId,
            @RequestBody MessageDTO content) {
        Discussion updatedDiscussion = discussionService.addMessage(discussionId, content);
        return ResponseEntity.ok().body(updatedDiscussion);
    }
    @GetMapping("/getAllDiscussions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DiscussionDTO>> getAllDiscussionsInfo() {
        List<DiscussionDTO> discussionsInfo = discussionService.getAllDiscussionsInfo();
        return ResponseEntity.ok(discussionsInfo);
    }

    @PutMapping("/{discussionId}/update-views")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> updateViews(@PathVariable Long discussionId, @RequestHeader("Authorization") String token) {
        try {
            Long newViews = discussionService.updateViews(discussionId, token);
            return ResponseEntity.ok(newViews);
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DiscussionNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MalformedJwtException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/created-by-current-user")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsCreatedByCurrentUser() {
        List<DiscussionDTO> discussions = discussionService.getDiscussionsCreatedByCurrentUser();
        return ResponseEntity.ok(discussions);
    }


    @DeleteMapping("/{discussionId}")
    public ResponseEntity<String> deleteDiscussionAndComments(@PathVariable Long discussionId) {
        discussionService.deleteDiscussionAndComments(discussionId);
        return new ResponseEntity<>("Discussion and associated comments deleted successfully.", HttpStatus.OK);
    }

}
