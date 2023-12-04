package Ensak.Blanat.Blanat.DTOs.discDTO;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionDTO {
        private Long id;
        private String titre;
        private String description;
        private int nbrvue;
        private Categories categories;
        private UserApp createur;


}