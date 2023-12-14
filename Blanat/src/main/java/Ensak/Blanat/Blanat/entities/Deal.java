package Ensak.Blanat.Blanat.entities;

import Ensak.Blanat.Blanat.enums.Categories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long deal_id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Categories category;

    @Column(nullable = false)
    private Date date_start;

    @Column(nullable = false)
    private Date date_end;

    @Column(nullable = false)
    private String price_initial;

    @Column(nullable = false)
    private String price_final;

    @Column(nullable = true)
    private String localisation;

    @Column(nullable = true)
    private float delivery_price;

    @Column(nullable = true)
    private int vote_up;

    @Column(nullable = true)
    private int vote_down;

    @Formula("(SELECT COUNT(c.comment_id) FROM Comment c WHERE c.deal_id = deal_ID)")
    private int comment_count;

    @Column(nullable = false)
    @ColumnDefault(value = "CURRENT_DATE")
    private LocalDate creation_date;

    @OneToMany(mappedBy = "deal")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "deal")
    private Collection<SavedDeals> saved_deals;

    @ManyToOne
    private UserApp deal_creator;




}
