package Ensak.Blanat.Blanat.controllers.commentController;


import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentRequest;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.services.commentServices.commentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class commentController {

    private final commentServiceInterface commentService;

    @Autowired
    public commentController(commentServiceInterface commentService) {
        this.commentService = commentService;
    }

    // POST method to add a new comment
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest, @RequestHeader("Authorization") String token) {
        try{
        // Extract user from token and create the comment
        Comment comment = commentService.createComment(token, commentRequest.getDealId(), commentRequest.getContent());

        // Instead of saving, just return a success response
        return new ResponseEntity<>("Comment data received successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing comment data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET method to retrieve comments for a specific deal
    @GetMapping("/deal/{dealId}")
    public ResponseEntity<List<CommentDTO>> getCommentsForDeal(@PathVariable Long dealId) {
        // Retrieve comments for the specified deal
        List<CommentDTO> commentDTOList = commentService.getCommentsForDeal(dealId);

        if (commentDTOList != null) {
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            // Handle the case where the deal is not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
