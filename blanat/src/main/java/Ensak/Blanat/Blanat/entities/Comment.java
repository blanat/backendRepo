package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    private LocalDateTime date;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserApp user;

    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

}
