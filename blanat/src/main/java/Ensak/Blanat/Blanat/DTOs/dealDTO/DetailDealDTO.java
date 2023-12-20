package Ensak.Blanat.Blanat.DTOs.dealDTO;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailDealDTO {

    private List<String> imagesUrl;
    //list of URLs of images related to a deal

    private UserDTO dealCreator;
    //userName (+ profile image)

    private List<CommentDTO> comments;
    //timeSincePosted(to be calculated) , content , userName ,  (+ profile image)

}
