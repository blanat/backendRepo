package Ensak.Blanat.Blanat.DTOs.discDTO;
import Ensak.Blanat.Blanat.enums.Categories;
import lombok.*;

import java.util.List;

@Data
public class DiscussionDTO {
        @Getter
        private Long id;
        private String titre;
        private String description;
        private Categories categories;
        private String createurUsername;
        private int nbrvue;
        private String profileImageUrl;
        private List<MessageDTO> messageDTO;
        private int save;

        public String getProfileImageUrl() {
                return profileImageUrl;
        }

        public void setProfileImageUrl(String profileImageUrl) {
                this.profileImageUrl = profileImageUrl;
        }

        public List<MessageDTO> getMessageDTO() {
                return messageDTO;
        }

        public void setMessageDTO(List<MessageDTO> messageDTO) {
                this.messageDTO = messageDTO;
        }

        public DiscussionDTO() {
        }

        public void setId(Long id) {
                this.id = id;
        }

        public DiscussionDTO(Long id, String titre, Categories categories, String username, int nbrvue, String profileImageUrl) {
                this.id = id;
                this.titre = titre;
                this.categories = categories;
                this.createurUsername = username;
                this.nbrvue = nbrvue;
                this.profileImageUrl = profileImageUrl; // Initialiser l'URL de l'image de profil
        }

}

