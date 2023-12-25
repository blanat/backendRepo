package Ensak.Blanat.Blanat.DTOs.commentDTO;


import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String timeSincePosted;
    private String content;
    //---------------------------
    private UserDTO dealCreator;
    //userName (+ profile image)
    //---------------------------


}