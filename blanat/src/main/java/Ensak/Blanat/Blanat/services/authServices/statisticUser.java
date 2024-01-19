package Ensak.Blanat.Blanat.services.authServices;

import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.exeptions.DiscussionServiceException;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.repositories.DiscMessageRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class statisticUser {


    @Autowired
    DiscussionRepository discussionRepository;
    /*DealRepository dealRepository;
    CommentRepository commentRepository;
    DiscMessageRepository discMessageRepository;*/


    public Long countDiscussionsCreatedByCurrentUser() {
        try {
            UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return discussionRepository.countByCreateur(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiscussionServiceException("Une erreur s'est produite lors du comptage des discussions.");
        }
    }

/*

    public Long countDealCreatedByCurrentUser() {
        try {
            UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return dealRepository.countBydealCreator(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiscussionServiceException("Une erreur s'est produite lors du comptage des discussions.");
        }
    }


    public Long countCommentsCreatedByCurrentUser() {
        try {
            UserApp currentUser = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return commentRepository.countByuser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiscussionServiceException("Une erreur s'est produite lors du comptage des discussions.");
        }
    }

*/


}
