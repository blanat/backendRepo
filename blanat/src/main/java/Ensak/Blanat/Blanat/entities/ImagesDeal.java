package Ensak.Blanat.Blanat.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FILE_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImagesDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

}