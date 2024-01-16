package Ensak.Blanat.Blanat.config.appconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AppConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Value("${app.deal-image-path}")
    private String dealImagePath;

    @Value("${app.profile-image-path}")
    private String profileImagePath;
    

    public String getDealImagePath() {
        return dealImagePath;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }




}
