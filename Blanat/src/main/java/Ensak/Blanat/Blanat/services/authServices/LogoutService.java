package Ensak.Blanat.Blanat.services.authServices;

import Ensak.Blanat.Blanat.repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        
        final String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        final String jwt;
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println(jwt);
        var storedToken = tokenRepository.findByToken(jwt).get();
        try {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }catch (Exception e){
            e.getStackTrace();
        }


    }
}
