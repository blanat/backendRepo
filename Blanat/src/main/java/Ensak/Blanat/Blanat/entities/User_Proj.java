package Ensak.Blanat.Blanat.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "user_Table")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User_Proj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate date_Adherence;

    @OneToMany(mappedBy = "user")
    private Collection<Comment> comments;

}


