package Ensak.Blanat.Blanat.DTOs.ethDoa;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private Long id;

    private String userName;

    private String email;

    private LocalDateTime createdAt;

    private String profileFilePath;


}
