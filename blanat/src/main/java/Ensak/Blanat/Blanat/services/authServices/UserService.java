package Ensak.Blanat.Blanat.services.authServices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Ensak.Blanat.Blanat.DTOs.ethDoa.ProfileDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserProfileStatisticsDTO;
import Ensak.Blanat.Blanat.entities.*;
import Ensak.Blanat.Blanat.mappers.UserMapper;
import Ensak.Blanat.Blanat.repositories.*;
import Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageProfileRepository imageProfileRepository;
    private final JwtService jwtService;
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final DiscMessageRepository discMessageRepository;
    private final PasswordEncoder passwordEncoder;
    private final DiscussionViewRepository discussionViewRepository;

    private final UserMapper userMapper;

    public UserApp updatePassword(String email, String newPassword) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public UserApp updateUserName(String email, String username) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setUserName(username);
        return userRepository.save(user);
    }


    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
    public UserApp save(UserApp newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }
        Path filePath = Path.of("C:\\ImageprofileUser\\nassima.jpg");
        newUser.setProfileFilePath(filePath.toString());

        newUser.setUpdatedAt(LocalDateTime.now());
        UserApp savedUser = userRepository.save(newUser);

        // Log the saved user for debugging
        log.debug("Saved user: {}", savedUser);

        return savedUser;
    }

    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }


    public UserApp getUserFromToken(String token) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Extract email from the token
        String email = jwtService.extractUserName(token);

        log.debug("User from token: {}", email);


        // Retrieve the user from the database by email
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Log the user for debugging
        log.debug("User from token: {}", user);
        System.out.println("nono"+user.getUserName());
        String userName=user.getUserName();
        System.out.println("nono"+user.getUserName());

        return user;
    }


    public ProfileDTO getUserFromToken2(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Extract email from the token
        String email = jwtService.extractUserName(token);

        log.debug("User from token: {}", email);

        // Retrieve the user from the database by email
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Log the user for debugging
        log.debug("User from token: {}", user);

        // Use the mapper to convert UserApp to ProfileDTO
        ProfileDTO profileDTO = userMapper.profileToProfileDTO(user);
        profileDTO.setProfileFilePath(imageUrlBuilder.buildProfileImageUrl(user.getProfileFilePath()));

        return profileDTO;
    }
/*

    // Extract email from the token
    String email = jwtService.extractUserName(token);

    log.debug("User from token: {}", email);

    // Retrieve the user from the database by email
    UserApp user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Log the user for debugging
    log.debug("User from token: {}", user);

    // Use the mapper to convert UserApp to ProfileDTO
    ProfileDTO profileDTO = userMapper.profileToProfileDTO(user);
    profileDTO.setProfileFilePath(imageUrlBuilder.buildProfileImageUrl(user.getProfileFilePath()));

    return profileDTO;
}


*/

    public UserApp getUserByUsername(String email) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }






    public UserApp deleteUser(String email, String password) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(password);
        return userRepository.save(user);
    }




    public UserProfileStatisticsDTO getUserDetails(String email){
        UserProfileStatisticsDTO userInfo = new UserProfileStatisticsDTO();
        Optional<UserApp> byEmail = userRepository.findByEmail(email);
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long id = user.getId();
        int numberOfSavedDeals = user.getSavedDeals().size();
        int numberOfSavedDiscu = user.getDiscussions().size();
        String userName = user.getUserName();
        LocalDateTime joinedAt = user.getCreatedAt();
        String profileFilePath = user.getProfileFilePath();

        return userInfo.builder()
                .numberOfDeals(numberOfSavedDeals)
                .DateJoined(joinedAt)
                .id(id)
                .userName(userName)
                .profileImageUrl(profileFilePath)
                .numberOfSavedDis(numberOfSavedDiscu)
                .build();




    }

    public void follow(String userId, String followerId) {
        UserApp user = userRepository.getById(Long.valueOf(userId));
        UserApp follower = userRepository.getById(Long.valueOf(followerId));

        user.getFollowers().add(follower);
        userRepository.save(user);
    }

    public void unFollow(String userId, String followerId) {
        UserApp user = userRepository.getById(Long.valueOf(userId));
        UserApp follower = userRepository.getById(Long.valueOf(followerId));

        user.getFollowers().remove(follower);
        userRepository.save(user);
    }

    public void changeProfilePicture(String email, MultipartFile newPfp) throws IOException {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getProfileFilePath() != null) {
            ImageProfile oldImage = imageProfileRepository.findByUserApp(user);
            imageProfileRepository.delete(oldImage);
            Path oldPath = Paths.get(user.getProfileFilePath());
            Files.delete(oldPath);

        }
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String filePath = absolutePath + "/src/main/resources/static/images/";
        byte[] bytes = newPfp.getBytes();
        Path path = Paths.get(filePath + newPfp.getOriginalFilename());
        Files.write(path, bytes);

        ImageProfile imageProfile = new ImageProfile();
        imageProfile.setName(newPfp.getOriginalFilename());
        imageProfile.setFilePath(path.toString());
        imageProfile.setUserApp(user);

        user.setProfileFilePath(path.toString());

        imageProfileRepository.save(imageProfile);
        userRepository.save(user);
    }

/*
* SELECT COUNT(*) AS num_saved_deals
FROM saved_deals
WHERE user_id = :userId;
* */




}