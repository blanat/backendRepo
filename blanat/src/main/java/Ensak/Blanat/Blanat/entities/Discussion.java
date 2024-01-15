package Ensak.Blanat.Blanat.entities;
import Ensak.Blanat.Blanat.enums.Categories;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Discussion")
public class Discussion {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private int nbrvue;
    private int save;


    @ManyToMany
    @JoinTable(
            name = "discussion_views",
            joinColumns = @JoinColumn(name = "discussion_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore // Pour éviter la sérialisation cyclique
    private Set<UserApp> viewers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    Categories categories;



    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserApp createur;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<DiscMessage> discMessage;

        public Discussion(String titre, String description, Categories categories) {
    }

    public void setDiscMessage(List<DiscMessage> discMessage) {
        this.discMessage = discMessage;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }



    public Categories getCategories() {
        return categories;
    }

    public int getSave() {return save;}

    public void setSave(int save) {this.save = save;}


    public List<DiscMessage> getDiscMessage() {
        return discMessage;
    }
}