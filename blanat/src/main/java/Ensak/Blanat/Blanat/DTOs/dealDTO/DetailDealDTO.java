package Ensak.Blanat.Blanat.DTOs.dealDTO;

import Ensak.Blanat.Blanat.DTOs.commentDTO.CommentDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;


import java.util.List;

public class DetailDealDTO {

    private List<String> ImagesURL;
    //list of URLs of images related to a deal

    private UserDTO DealCreator;
    //userName

    private List<CommentDTO> Comments;
    //TimeSincePosted , Content , UserName;


}
