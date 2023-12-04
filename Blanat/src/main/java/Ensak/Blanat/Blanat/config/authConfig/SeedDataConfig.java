package Ensak.Blanat.Blanat.config.authConfig;


import Ensak.Blanat.Blanat.enums.Role;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        
      if (userRepository.count() == 0) {

        UserApp admin = UserApp
                      .builder()
                      .userName("admin")
                      //.lastName("admin")
                      .email("admin@admin.com")
                      .password(passwordEncoder.encode("password"))
                      .role(Role.ROLE_ADMIN)
                      .build();

        userService.save(admin);
        log.debug("created ADMIN user - {}", admin);
      }
    }

}
