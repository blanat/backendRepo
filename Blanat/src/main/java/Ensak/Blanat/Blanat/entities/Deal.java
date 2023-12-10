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
    private long deal_ID;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Categories category;

    @Column(nullable = false)
    private Date date_debut;

    @Column(nullable = false)
    private Date date_fin;

    @Column(nullable = false)
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

    @Column(nullable = false)
    @ColumnDefault(value = "CURRENT_DATE")
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "deal")
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "deal")
    private Collection<SavedDeals> savedDeals;

    @ManyToOne
    private UserApp dealCreator;

    public long getDeal_ID() {
        return deal_ID;
    }

    public void setDeal_ID(long deal_ID) {
        this.deal_ID = deal_ID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public float getLivraison_prix() {
        return livraison_prix;
    }

    public void setLivraison_prix(float livraison_prix) {
        this.livraison_prix = livraison_prix;
    }

    public int getVote_up() {
        return vote_up;
    }

    public void setVote_up(int vote_up) {
        this.vote_up = vote_up;
    }

    public int getVote_down() {
        return vote_down;
    }

    public void setVote_down(int vote_down) {
        this.vote_down = vote_down;
    }

    public int getNbre_comment() {
        return nbre_comment;
    }

    public void setNbre_comment(int nbre_comment) {
        this.nbre_comment = nbre_comment;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<SavedDeals> getSavedDeals() {
        return savedDeals;
    }

    public void setSavedDeals(Collection<SavedDeals> savedDeals) {
        this.savedDeals = savedDeals;
    }

    public UserApp getDealCreator() {
        return dealCreator;
    }

    public void setDealCreator(UserApp dealCreator) {
        this.dealCreator = dealCreator;
    }


    public interface DealProjection {
        Long getDeal_ID();
        String getTitre();
        String getDescription();
        Categories getCategory();
        Date getDate_debut();
        Date getDate_fin();
        String getPrix();
        String getLocalisation();
        float getLivraison_prix();
        int getVote_up();
        int getVote_down();
        int getNbre_comment();
        LocalDate getDateCreation();
        Collection<Comment> getComments();
        Collection<SavedDeals> getSavedDeals();
        UserApp getDealCreator();
    }
}
