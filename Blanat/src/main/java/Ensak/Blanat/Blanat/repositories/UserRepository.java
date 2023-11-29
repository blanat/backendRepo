package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.User_Proj;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User_Proj, Long> {
}
