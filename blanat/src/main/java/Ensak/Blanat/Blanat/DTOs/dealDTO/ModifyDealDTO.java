package Ensak.Blanat.Blanat.DTOs.dealDTO;

import Ensak.Blanat.Blanat.enums.Categories;

public class ModifyDealDTO {
    private String title;
    private String Description;
    private Categories category;
    private float price;
    private float newPrice;
    private String localisation;
    private boolean deliveryExist;
    private float deliveryPrice;
    private String lienDeal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isDeliveryExist() {
        return deliveryExist;
    }

    public void setDeliveryExist(boolean deliveryExist) {
        this.deliveryExist = deliveryExist;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getLienDeal() {
        return lienDeal;
    }

    public void setLienDeal(String lienDeal) {
        this.lienDeal = lienDeal;
    }

    public ModifyDealDTO(String title, String description, Categories category, float price, float newPrice, String localisation, boolean deliveryExist, float deliveryPrice, String lienDeal) {
        this.title = title;
        Description = description;
        this.category = category;
        this.price = price;
        this.newPrice = newPrice;
        this.localisation = localisation;
        this.deliveryExist = deliveryExist;
        this.deliveryPrice = deliveryPrice;
        this.lienDeal = lienDeal;
    }
}
