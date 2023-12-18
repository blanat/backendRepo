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
public class ListDealDTO {
    private long deal_ID;
    private String title;
    private String description;
    private Categories category;//filtrer
    private Date date_fin;//pour mentionner l approche de fin d un deal
    private float prix_A;
    private float prix_N;
    private String localisation;
    private boolean livraisonExist;
    private float livraison_prix;
    private int deg;//filter
    private int nbre_comment;//filter
    private String lienDeal;

    //image and time will be added to the dto
    private String FirstImageURL;
    private String timePassedSinceCreation;//filter
}

