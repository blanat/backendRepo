package Ensak.Blanat.Blanat.DTOs.discDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class discussionDTO {

        private String titre;
        private String description;
        private Long userID;




}
