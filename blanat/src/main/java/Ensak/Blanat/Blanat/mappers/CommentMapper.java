package Ensak.Blanat.Blanat.mappers;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.entities.Comment;
import Ensak.Blanat.Blanat.util.General;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper; // Add this

    public CommentMapper(ModelMapper modelMapper, UserMapper userMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
    }
    public CommentDTO commentToCommentDTO(Comment comment) {
        String timePassed = General.calculateTimePassed(comment.getDate());

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setTimeSincePosted(timePassed);
        //new for userCreator we add the usename and imageProfile
        UserDTO dealCreatorDTO = userMapper.userToUserDTO(comment.getUser());
        commentDTO.setDealCreator(dealCreatorDTO);

       return commentDTO;
    }
}
