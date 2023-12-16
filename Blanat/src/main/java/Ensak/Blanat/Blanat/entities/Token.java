package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    private boolean expired;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "userApp_id")
    private UserApp userApp;
}
