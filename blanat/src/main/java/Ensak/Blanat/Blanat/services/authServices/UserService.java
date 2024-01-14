package Ensak.Blanat.Blanat.services.authServices;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Ensak.Blanat.Blanat.DTOs.userDTO.UserProfileStatisticsDTO;
import Ensak.Blanat.Blanat.entities.*;
import Ensak.Blanat.Blanat.repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final DiscMessageRepository discMessageRepository;


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
        Path filePath = Path.of("E:\\ImageprofileUser\\imagesDefault.png");
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

        return user;
    }
    public UserApp getUserByUsername(String email) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public UserApp updatePassword(String email, String newPassword) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }





    @Transactional
    public void deleteUser(String email) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Remove associations
        for (Discussion discussion : user.getDiscussions()) {
            discussion.getViewers().remove(user);
            discussion.getDiscMessage().clear();
        }

        // Remove user from views of other discussions
        List<Discussion> otherDiscussions = discussionRepository.findAllByViewersContaining(user);
        for (Discussion discussion : otherDiscussions) {
            discussion.getViewers().remove(user);
        }

        // Clear associations from comments and deals
        for (Comment comment : user.getComments()) {
            comment.getDeal().getComments().remove(comment);
        }
        for (Deal deal : user.getDeals()) {
            deal.getComments().clear();
            deal.getDealCreator().getDeals().remove(deal);
        }

        userRepository.delete(user);
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

/*
* SELECT COUNT(*) AS num_saved_deals
FROM saved_deals
WHERE user_id = :userId;
* */




}
