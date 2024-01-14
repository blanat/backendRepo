package Ensak.Blanat.Blanat.DTOs.ethDoa;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
@AllArgsConstructor
@Data
@Getter
public class ProfileDTO {

    Long id;

    String userName;

    String email;

}
