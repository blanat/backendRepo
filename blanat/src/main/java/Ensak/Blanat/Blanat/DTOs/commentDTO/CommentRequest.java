package Ensak.Blanat.Blanat.DTOs.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private long dealId;
    private String content;


}
