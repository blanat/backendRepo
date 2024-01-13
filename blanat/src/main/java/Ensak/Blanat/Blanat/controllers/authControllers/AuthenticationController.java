package Ensak.Blanat.Blanat.controllers.authControllers;



import Ensak.Blanat.Blanat.DTOs.ethDoa.JwtAuthenticationResponse;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignInRequest;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignUpRequest;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.authServices.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@RequestBody SignUpRequest request) {
        //call validation
        return authenticationService.signup(request);
    }



    @PostMapping("/signin")
    public JwtAuthenticationResponse signin(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}