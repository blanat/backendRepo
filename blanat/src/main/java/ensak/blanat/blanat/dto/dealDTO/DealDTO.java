package Ensak.Blanat.Blanat.DTOs.dealDTO;

import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.enums.Comments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {
    private Long id;
    private String titre;
    private String description;
    private Categories categories;
    private UserApp createur;
    private String image;
    private String prix;
    private Comments commentaires;
    private Date date_debut;
    private Date date_fin;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public UserApp getCreateur() {
        return createur;
    }

    public void setCreateur(UserApp createur) {
        this.createur = createur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Comments getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Comments commentaires) {
        this.commentaires = commentaires;
    }
}
