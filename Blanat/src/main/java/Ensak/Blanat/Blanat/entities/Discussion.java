package Ensak.Blanat.Blanat.entities;
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
    private int nombreVues;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp Creator;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNombreVues(int nombreVues) {
        this.nombreVues = nombreVues;
    }

    public void setCreator(UserApp creator) {
        Creator = creator;
    }
}
