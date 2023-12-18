package Ensak.Blanat.Blanat.DTOs.DealDTO;

import Ensak.Blanat.Blanat.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateDealDTO {

    private String title;

    private String description;
/*

    private String lienDeal;

    private Categories category;

    private Date date_debut;

    private Date date_fin;

    private float prix;

    private String localisation;

    private boolean livraisonExist;

    private float livraison_prix;
*/

}
