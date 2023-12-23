package Ensak.Blanat.Blanat.entities;

import Ensak.Blanat.Blanat.enums.Categories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private long dealID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)//testing
    private String description;

    @Column(nullable = true)//testing
    private String lienDeal;

    @Column(nullable = true)//testing
    @Enumerated(EnumType.STRING)
    private Categories category;

    @Column(nullable = true)//testing
    private Date dateDebut;

    @Column(nullable = true)//testing
    private Date dateFin;

    @Column(nullable = true)//testing
    private float price;

    @Column(nullable = true)//testing
    private float newPrice;

    @Column(nullable = true)
    private String localisation;

    @Column(nullable = true)
    private boolean deliveryExist;

    @Column(nullable = true)
    private float deliveryPrice;

    @Column(nullable = true)
    private int deg;

    @Formula("(SELECT COUNT(c.comment_id) FROM Comment c WHERE c.deal_id = dealID)")
    private int numberComment;

    @Column(nullable = true)// should be false, but because we want to test we will be changing it to true
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "deal")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "deal")
    private Collection<SavedDeals> savedDeals;

    @ManyToOne
    private UserApp dealCreator;

    @OneToMany(mappedBy = "deal")
    private Collection<ImagesDeal> imagesDeals;


}