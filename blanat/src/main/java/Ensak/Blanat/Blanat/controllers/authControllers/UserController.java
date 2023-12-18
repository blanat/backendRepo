package Ensak.Blanat.Blanat.controllers.authControllers;

import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserApp> getAllUsers() {
        return userService.getAllUsers();
    }

    // You can add more methods for handling other user-related operations

    @PutMapping("/{email}/password")
    public UserApp updatePassword(@PathVariable String email, @RequestBody String newPassword) {
        return userService.updatePassword(email, newPassword);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
    }


}
