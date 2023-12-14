package Ensak.Blanat.Blanat.entities;
import Ensak.Blanat.Blanat.enums.Categories;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;
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
    private Deal deal;

}