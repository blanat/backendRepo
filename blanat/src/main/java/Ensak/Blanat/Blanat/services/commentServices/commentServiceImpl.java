package Ensak.Blanat.Blanat.services.commentServices;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.entities.Deal;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.mappers.CommentMapper;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.CommentRepository;
import Ensak.Blanat.Blanat.repositories.DealRepository;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class commentServiceImpl implements commentServiceInterface {

    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public commentServiceImpl(CommentRepository commentRepository, DealRepository dealRepository,
                              CommentMapper commentMapper, UserMapper userMapper,UserService userService) {
        this.commentRepository = commentRepository;
        this.dealRepository = dealRepository;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public List<CommentDTO> getCommentsForDeal(long dealId) {
        // Retrieve the deal
        Deal deal = dealRepository.findById(dealId).orElse(null);

        if (deal != null) {
            // Retrieve comments for the deal ordered by date
            List<Comment> comments = commentRepository.findByDealOrderByDateAsc(deal);

            // Map and return the DTOs
            return comments.stream().map(commentMapper::commentToCommentDTO).collect(Collectors.toList());
        }

        return null; // Handle the case where the deal is not found
    }


    @Override
    public Comment createComment(String token, long dealId, String content) {

        UserApp user = userService.getUserFromToken(token);

        if (user != null) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setDate(LocalDateTime.now());
            comment.setUser(user);
            // Implement logic to set the deal for the comment (dealRepository.findById(dealId).orElse(null))
            Deal deal = dealRepository.findById(dealId).orElse(null);
            comment.setDeal(deal);

            return commentRepository.save(comment);
        }

        // If user is null, it means the token is invalid
        return null;
    }
}
