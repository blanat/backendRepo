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
    private long dealID; // corresponds to deal_ID in the entity
    private String title;
    private String description;
    private Categories category; // corresponds to category in the entity
    private Date dateFin; // corresponds to date_fin in the entity
    private float price; // corresponds to prix_A in the entity
    private float newPrice; // corresponds to prix_N in the entity
    private String localisation;
    private boolean deliveryExist; // corresponds to livraisonExist in the entity
    private float deliveryPrice; // corresponds to livraison_prix in the entity
    private int deg; // corresponds to deg in the entity
    private int numberOfComments; // corresponds to nbre_comment in the entity
    private String lienDeal;

    // Image and time will be added to the DTO
    private String firstImageUrl;
    private String timePassedSinceCreation; // corresponds to the same name in the entity
}
