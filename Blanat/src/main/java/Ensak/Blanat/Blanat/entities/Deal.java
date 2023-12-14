package Ensak.Blanat.Blanat.entities;

import Ensak.Blanat.Blanat.enums.Categories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Entity
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long deal_ID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)//testing
    private String description;

    @Column(nullable = true)//testing
    private Categories category;

    @Column(nullable = true)//testing
    private Date date_debut;

    @Column(nullable = true)//testing
    private Date date_fin;

    @Column(nullable = true)//testing
    private String prix;

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

    @Column(nullable = true)// should be false, but because we want to test we will be changing it to true
    //@Temporal(TemporalType.TIMESTAMP)//format de la date
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "deal")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "deal")
    private Collection<SavedDeals> savedDeals;

    @ManyToOne
    private UserApp dealCreator;

    @OneToMany(mappedBy = "deal")
    private Collection<ImagesDeal> imagesDeals;


}