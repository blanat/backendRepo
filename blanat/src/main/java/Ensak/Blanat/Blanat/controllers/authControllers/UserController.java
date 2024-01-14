package Ensak.Blanat.Blanat.controllers.authControllers;

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

    @PutMapping("/{email}/password")
    public UserApp updatePassword(@PathVariable String email, @RequestBody String newPassword) {
        return userService.updatePassword(email, newPassword);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }

    @PostMapping("/userDetails/{email}")
    public UserProfileStatisticsDTO getUserDetails(@PathVariable("email") String email) {
        //String email = email;
        UserProfileStatisticsDTO userDetails = userService.getUserDetails(email);
        return userDetails;
    }


}
