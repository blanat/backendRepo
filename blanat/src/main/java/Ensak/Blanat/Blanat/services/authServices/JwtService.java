package Ensak.Blanat.Blanat.services.authServices;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class JwtService {

private final UserRepository userRepository;
  String jwtSecretKey="1cd6a9c75006cdc80314f7e2813a9513f0ae46d17d652e34711fc4167325fb41";
  Long jwtExpirationMs = 399999990L;

    public String extractUserName(String token) {
        String userName = extractClaim(token, Claims::getSubject);
        return userName;
    }



    public String generateToken(UserDetails userDetails) {
      return generateToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
      final String userName = extractUserName(token);
      return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
      final Claims claims = extractAllClaims(token);
      return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
      return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
      return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
      return Keys.hmacShaKeyFor(keyBytes);
  }



    // Méthode pour extraire le nom d'utilisateur du JWT

    public String ExtractUserName(String jwt) {
        String userName = extractClaim(jwt, Claims::getSubject);
        return userName;
    }


}
