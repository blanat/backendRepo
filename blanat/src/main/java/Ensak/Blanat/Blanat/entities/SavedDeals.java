package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedDeals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Date dateSaving;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;

    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;


}
