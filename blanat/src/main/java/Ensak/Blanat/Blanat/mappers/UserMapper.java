package Ensak.Blanat.Blanat.mappers;

import Ensak.Blanat.Blanat.DTOs.ethDoa.ProfileDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.services.imagesDealService.imageURLbuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO userToUserDTO(UserApp user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setProfileImageUrl(imageURLbuilder.buildProfileImageUrl(user.getProfileFilePath()));
        return userDTO;
    }

    public ProfileDTO profileToProfileDTO(UserApp user){
        ProfileDTO profileDTO = modelMapper.map(user, ProfileDTO.class);
        profileDTO.setProfileFilePath(imageURLbuilder.buildProfileImageUrl(user.getProfileFilePath()));
        return profileDTO;
    }
}
