package Ensak.Blanat.Blanat.services.commentServices;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.mappers.CommentMapper;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.services.dealService.DealServiceImp;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class commentServiceImpl implements commentServiceInterface {

    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private DealServiceImp dealService;


    public commentServiceImpl(CommentRepository commentRepository, DealRepository dealRepository,
                              CommentMapper commentMapper,UserService userService,DealServiceImp dealService) {
        this.commentRepository = commentRepository;
        this.dealRepository = dealRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.dealService = dealService;
    }

    @Override
    public List<CommentDTO> getCommentsForDeal(long dealId) {
        // Retrieve the deal
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            // Retrieve comments for the deal ordered by date
            List<Comment> comments = commentRepository.findByDealOrderByDateAsc(deal);

            // Map and return the DTOs using Stream.toList()
            return comments.stream().map(commentMapper::commentToCommentDTO).toList();
        }

        return Collections.emptyList(); // Handle the case where the deal is not found
    }



    @Override
    public Comment createComment(String token, long dealId, String content) {
        UserApp user = userService.getUserFromToken(token);

        if (user != null) {
            Deal deal = dealRepository.findById(dealId).orElse(null);

            if (deal != null) {
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setDate(LocalDateTime.now());
                comment.setUser(user);
                comment.setDeal(deal);

                // Update the number of comments for the deal
                dealService.updateCommentCount(deal.getDealID());

                // Log the comment count if deal is not null
                System.out.println("=========== - Deal ID: " + deal.getDealID() + ", Comment Count: " + deal.getNumberOfComments());

                return commentRepository.save(comment);
            }
        }

        // If user is null or deal is null, return null
        return null;
    }

}
