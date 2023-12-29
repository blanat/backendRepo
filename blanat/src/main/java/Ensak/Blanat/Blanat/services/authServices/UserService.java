package Ensak.Blanat.Blanat.services.authServices;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.UserRepository;
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

    @Autowired
  private final UserRepository userRepository;
    private final JwtService jwtService;

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



    public void deleteUser(String email) {
        UserApp user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }






}
