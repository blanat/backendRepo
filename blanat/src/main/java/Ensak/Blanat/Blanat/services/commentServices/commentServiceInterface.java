package Ensak.Blanat.Blanat.services.commentServices;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.entities.Comment;

import java.util.List;

public interface commentServiceInterface {
    List<CommentDTO> getCommentsForDeal(long dealId);

    Comment createComment(String token, long dealId, String content);
}
