package Ensak.Blanat.Blanat.repositories;

import Ensak.Blanat.Blanat.entities.SavedDeals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedDealsRepository extends JpaRepository<SavedDeals, Long> {
    // You can add custom query methods here if needed
}
