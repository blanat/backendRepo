package Ensak.Blanat.Blanat.DTOs.ethDoa;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Getter
public class ProfileDTO {

    Long id;

    String userName;

    String email;

    LocalDateTime createdAt;
    
    String profileFilePath;


}
