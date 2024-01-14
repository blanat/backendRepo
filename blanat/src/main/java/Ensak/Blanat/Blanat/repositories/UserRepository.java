package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByEmail(String email);
    Optional<UserApp> findByUserName(String username);

    @Query("SELECT id FROM UserApp WHERE email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);



}
