package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserAppRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void UserRepositorySaved(){
        UserApp userApp = UserApp.builder()
                .userName("youssef zeaikor")
                .email("yosef@user.com")
                .password("password")
                .role(Role.ROLE_USER)
                .build();
        UserApp userSaved = userRepository.save(userApp);
        System.out.println(userSaved);
        Assertions.assertThat(userSaved.getEmail()).isNotNull();
        Assertions.assertThat(userSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepositoryFindByEmail(){
        UserApp userApp1 = UserApp.builder()
                .userName("youssef zeaikor")
                .email("yosef1@user.com")
                .password("password")
                .role(Role.ROLE_USER)
                .build();
        UserApp userSaved = userRepository.save(userApp1);

        UserApp userApp = userRepository.findByEmail(userSaved.getEmail()).get();
        Assertions.assertThat(userSaved.getEmail()).isEqualTo("yosef1@user.com");
    }




}
