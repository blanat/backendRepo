package Ensak.Blanat.Blanat.deal.user;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    // ... other mocks if needed

    @Test
    void testUpdatePassword() {
        // Mocking userRepository behavior
        when(userRepository.findByEmail("ahmed.fahim@uit.ac.ma")).thenReturn(Optional.of(new UserApp()));

        // Mocking jwtService behavior
        when(jwtService.extractUserName("Bearer mockToken")).thenReturn("ahmed.fahim@uit.ac.ma");

        // Your test logic
        // For example, calling the method you want to test
        userService.updatePassword("ahmed.fahim@uit.ac.ma", "newPassword");
    }


    @Test
    void testUpdatePassword_UserNotFound() {
        // Mocking userRepository behavior
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Your test logic
        // For example, asserting that the correct exception is thrown
        assertThrows(UsernameNotFoundException.class,
                () -> userService.updatePassword("nonexistent@example.com", "newPassword"));
    }

    // ... other tests for UserService
}
