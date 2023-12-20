package Ensak.Blanat.Blanat.mappers;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.services.imagesDealService.imageURLbuilder;
import Ensak.Blanat.Blanat.util.General;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    private final ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CommentDTO commentToCommentDTO(Comment comment) {
        String timePassed = General.calculateTimePassed(comment.getDate());

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setTimeSincePosted(timePassed);
        commentDTO.setUserName(comment.getUser().getUsername());
        commentDTO.setProfileImageUrl(imageURLbuilder.buildProfileImageUrl(comment.getUser().getProfileFilePath()));
        return commentDTO;
    }
}
