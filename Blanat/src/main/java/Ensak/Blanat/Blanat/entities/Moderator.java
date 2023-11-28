package Ensak.Blanat.Blanat.entities;


import jakarta.persistence.Entity;

@Entity
public class Moderator extends User {

    private boolean isSuperAdmin;
}
