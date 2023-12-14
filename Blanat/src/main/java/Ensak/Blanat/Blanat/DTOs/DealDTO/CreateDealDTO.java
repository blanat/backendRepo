package Ensak.Blanat.Blanat.DTOs.DealDTO;

import Ensak.Blanat.Blanat.entities.UserApp;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDealDTO {
    private String title;
    private String description;
}
