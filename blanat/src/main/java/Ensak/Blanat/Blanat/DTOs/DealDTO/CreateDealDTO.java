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

    private Date dateDebut;

    private Date dateFin;

    private float price;

    private String localisation;

    private boolean deliveryExist;

    private float deliveryPrice;

*/

}
