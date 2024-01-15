package Ensak.Blanat.Blanat.controllers.authControllers;

import Ensak.Blanat.Blanat.DTOs.ethDoa.ProfileDTO;
import Ensak.Blanat.Blanat.DTOs.userDTO.UserProfileStatisticsDTO;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/AllUsers")
    public List<UserApp> getAllUsers() {
        return userService.getAllUsers();
    }



    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }


    @PutMapping("/{email}")
    public ResponseEntity<Void> updatePassword(@PathVariable String email, @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("password");
        userService.updatePassword(email, newPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("username/{email}")
    public ResponseEntity<Void> updateUserName(@PathVariable String email, @RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        userService.updateUserName(email, username);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/userDetails/{email}")
    public UserProfileStatisticsDTO getUserDetails(@PathVariable("email") String email) {
        //String email = email;
        UserProfileStatisticsDTO userDetails = userService.getUserDetails(email);
        return userDetails;
    }


    @PostMapping("/follow")
    public void  followUser(@RequestBody Map<String, String> requestBody){
        String userId = requestBody.get("userId");
        String followerId=requestBody.get("followerId");
        userService.follow(userId,followerId);
    }

    @PostMapping("/unfollow")
    public void  unFollowUser(@RequestBody Map<String, String> requestBody){
        String userId = requestBody.get("userId");
        String followerId=requestBody.get("followerId");
        userService.unFollow(userId,followerId);
    }

    @GetMapping("/userFromToken")
    public UserApp fromToke(@RequestHeader("Authorization") String authorizationHeader ){
        return userService.getUserFromToken(authorizationHeader);
    }

    @GetMapping("/userFromToken2")
    public ProfileDTO fromToke2(@RequestHeader("Authorization") String authorizationHeader ){
        return userService.getUserFromToken2(authorizationHeader);
    }

}
