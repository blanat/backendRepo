package Ensak.Blanat.Blanat.services.notificationService;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingFirebaseService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public static void sendCommentNotification(String recipientToken, String discussionTitle, String commentContent) {
        Notification notification = Notification
                .builder()
                .setTitle("New Comment on Discussion: " + discussionTitle)
                .setBody(commentContent)
                .build();

        Message message = Message
                .builder()
                .setToken(recipientToken)
                .setNotification(notification)
                .build();


    }
}
