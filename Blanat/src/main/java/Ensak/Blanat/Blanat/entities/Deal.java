package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long deal_ID;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date_debut;

    @Column(nullable = false)
    private Date date_fin;

    @Column(nullable = false)
    private float prix;

    @Column(nullable = true)
    private String localisation;

    @Column(nullable = true)
    private float livraison_prix;

    @Column(nullable = true)
    private int vote_up;

    @Column(nullable = true)
    private int vote_down;

    @Formula("(SELECT COUNT(c.comment_id) FROM Comment c WHERE c.deal_id = deal_ID)")
    private int nbre_comment;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "deal")
    private Collection<Comment> comments;
}
