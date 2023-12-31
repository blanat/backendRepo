package Ensak.Blanat.Blanat.services.authServices;

import Ensak.Blanat.Blanat.DTOs.ethDoa.JwtAuthenticationResponse;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignInRequest;
import Ensak.Blanat.Blanat.DTOs.ethDoa.SignUpRequest;
import Ensak.Blanat.Blanat.enums.Role;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


  public JwtAuthenticationResponse signup(SignUpRequest request) {
      var user = UserApp
                  .builder()
                  .userName(request.getUserName())
                  //.lastName(request.getLastName())
                  .email(request.getEmail())
                  .password(passwordEncoder.encode(request.getPassword()))
                  .role(Role.ROLE_USER)
                  .build();

      user = userService.save(user);
      var jwt = jwtService.generateToken(user);
      return JwtAuthenticationResponse.builder().token(jwt).build();
  }


  public JwtAuthenticationResponse signin(SignInRequest request) {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      var user = userRepository.findByEmail(request.getEmail())
              .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
      var jwt = jwtService.generateToken(user);
      return JwtAuthenticationResponse.builder().token(jwt).build();
  }
  
}
