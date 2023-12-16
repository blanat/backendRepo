package Ensak.Blanat.Blanat.filters.authFilters;

import java.io.IOException;


import Ensak.Blanat.Blanat.repositories.TokenRepository;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import org.apache.commons.lang3.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /* this filter intercepts incoming requests, checks
     for a valid JWT in the "Authorization" header, validates
      the token, and sets the authenticated user in the
       Spring Security context if the token is valid*/

  private final JwtService jwtService;
  private final UserService userService;

  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, 
        FilterChain filterChain)
        throws ServletException, IOException {
      final String authHeader = request.getHeader("Authorization");
      final String jwt;
      final String userEmail;
      if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
          filterChain.doFilter(request, response);
          return;
      }
      jwt = authHeader.substring(7);
      log.debug("JWT - {}", jwt.toString());
      userEmail = jwtService.extractUserName(jwt);
      if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

//          --------
          var isTokenValidAfterLogout = tokenRepository.findByToken(jwt)
                  .map(token -> !token.isExpired() && !token.isRevoked())
                  .orElse(false);
//          --------
          if (jwtService.isTokenValid(jwt, userDetails) && isTokenValidAfterLogout) {
            log.debug("User - {}", userDetails);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
          }
      }
      filterChain.doFilter(request, response);
  }
}