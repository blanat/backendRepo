package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.DTOs.ethDoa.JwtAuthenticationResponse;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignInRequest;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignUpRequest;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.services.authServices.AuthenticationService;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserAppAuthTest {

//    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoderMock;
    @Mock
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private AuthenticationService authenticationService;
    private UserService userService;




    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class,RETURNS_DEEP_STUBS);
        jwtService = new JwtService();
        userService = mock(UserService.class, RETURNS_DEEP_STUBS);
        authenticationService = new AuthenticationService(userRepository,userService,passwordEncoderMock,jwtService,authenticationManager);
    }



    @Test
    public void UserSignupRequestTest() {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userName("youssef")
                .email("yosef@user.com")
                .password("password")
                .build();

        when(userService.save(any(UserApp.class))).thenReturn(mock(UserApp.class));

        JwtAuthenticationResponse signupResponse = authenticationService.signup(signUpRequest);
        System.out.println(signupResponse.getToken());
        Assertions.assertThat(signupResponse.getToken()).isNotNull();

    }


        @Test
        public void UserSigningRequestCorrectCredentialsTest(){
            UserApp admin = UserApp.builder()
                    .id(1L)
                    .email("admin@admin")
                    .password("password")
                    .build();

            when(userRepository.findByEmail("admin@admin.com"))
                    .thenReturn(Optional.ofNullable(admin));

            SignInRequest signInRequest =SignInRequest.builder()
                .email("admin@admin.com")
                .password("password")
                .build();


        JwtAuthenticationResponse signinResponse = authenticationService.signin(signInRequest);
            System.out.println(signinResponse.getToken());
            Assertions.assertThat(signinResponse.getToken()).isNotNull();
    }

    @Test
    public void UserSigningRequestBadCredentialsTest(){

        SignInRequest signInRequest =SignInRequest.builder()
                .email("youssef@admin.com")
                .password("password")
                .build();

        assertThrows(IllegalArgumentException.class, ()-> authenticationService.signin(signInRequest));
    }
}
