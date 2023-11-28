package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {
    @Id
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

    //private int nbre_comment;

    @Column(nullable = false)
    private Date dateCreation;
}
