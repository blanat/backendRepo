package Ensak.Blanat.Blanat.DTOs.commentDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String timeSincePosted;
    private String content;
    private String userName;
    private String profileImageUrl;

}