package Ensak.Blanat.Blanat.DTOs.userDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileStatisticsDTO{
    private Long id;
    private String userName;
    private String profileImageUrl;
    private int numberOfDeals;
    private int numberOfSavedDis;
    private LocalDateTime DateJoined;
}
