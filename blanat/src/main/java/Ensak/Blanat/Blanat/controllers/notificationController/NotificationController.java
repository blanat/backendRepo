package Ensak.Blanat.Blanat.controllers.notificationController;

import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.services.discuService.IDiscussionService;
import Ensak.Blanat.Blanat.services.notificationService.FirebaseMessagingService;
import Ensak.Blanat.Blanat.services.notificationService.MessagingFirebaseService;
import Ensak.Blanat.Blanat.services.notificationService.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;
    @Autowired
    IDiscussionService discussionService;
    @PostMapping
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }


    @PostMapping("/{discussionId}/comment")
    public String addComment(@PathVariable Long discussionId, @RequestBody MessageDTO discMessage) {
        // Add the comment to the discussion
        Discussion discussion = discussionService.addMessage(discussionId, discMessage);

        // Get the creator of the discussion
        String creatorToken = discussion.getCreateur().getFirebaseToken();

        // Send notification to the discussion creator
        MessagingFirebaseService.sendCommentNotification(creatorToken, discussion.getTitre(), discMessage.getContent());

        return "Comment added successfully.";
    }
}
