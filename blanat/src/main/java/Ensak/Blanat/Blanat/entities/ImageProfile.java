package Ensak.Blanat.Blanat.entities;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
public class ImageProfile {
    @GeneratedValue
    @Id
    private Long id;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String name;

    private String filePath;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserApp userApp;

    public ImageProfile(Long id, String type, String name, UserApp userApp) {
        this.id = id;
        this.name = name;
        this.userApp = userApp;
    }

    public ImageProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
}
