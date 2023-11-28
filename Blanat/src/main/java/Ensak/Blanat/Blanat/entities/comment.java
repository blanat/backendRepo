package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class comment {
    @Id
    @Column(nullable = false)
    private long Comment_ID;

}
