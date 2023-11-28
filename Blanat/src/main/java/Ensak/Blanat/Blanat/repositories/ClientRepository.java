package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
