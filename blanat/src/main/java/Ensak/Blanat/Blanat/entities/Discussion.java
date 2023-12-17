package Ensak.Blanat.Blanat.entities;
import Ensak.Blanat.Blanat.enums.Categories;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private int nbrvue;

    @Enumerated(EnumType.STRING)
    Categories categories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp createur;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNbrvue(int nbrvue) {
        this.nbrvue = nbrvue;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public void setCreateur(UserApp createur) {
        this.createur = createur;
    }


}