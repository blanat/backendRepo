package Ensak.Blanat.Blanat.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Moderator extends User_Proj {

    private boolean isSuperAdmin;

    //when the moderator want to sign up for the first time, the system searches for the email given in the client table.
    // if the moderator does have a client account, we will ask him/her if they want to use the same account.

}
