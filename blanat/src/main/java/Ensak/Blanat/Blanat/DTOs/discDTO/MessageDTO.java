package Ensak.Blanat.Blanat.DTOs.discDTO;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class MessageDTO {
    private String content;
    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime createdAt;
    private String userName;
    private String profileImageUrl;

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
