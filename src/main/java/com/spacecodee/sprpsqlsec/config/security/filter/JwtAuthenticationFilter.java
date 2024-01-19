package com.spacecodee.sprpsqlsec.config.security.filter;

import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.persistence.entity.JwtTokenEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IJwtTokenRepository;
import com.spacecodee.sprpsqlsec.service.IJwtService;
import com.spacecodee.sprpsqlsec.service.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserService userService;
    private final IJwtTokenRepository jwtRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        //authorization
        var jwt = this.jwtService.extractJwtFromRequest(request);
        if (jwt == null || !StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<JwtTokenEntity> token = this.jwtRepository.findByToken(jwt);
        var isValid = this.validateToken(token);

        if (!isValid) {
            filterChain.doFilter(request, response);
            return;
        }

        var username = this.jwtService.extractUsername(jwt);

        var userD = this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User isn't found with Username: " + username + "."));

        var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userD.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Optional<JwtTokenEntity> optionalJwtToken) {
        if (!optionalJwtToken.isPresent()) {
            System.out.println("Token is not present");
            return false;
        }

        JwtTokenEntity token = optionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());
        var isValid = token.isValid() && token.getExpiryDate().after(now);

        if (!isValid) {
            System.out.println("Token is not valid");
            this.updateTokenStatus(token);
        }

        return isValid;
    }

    private void updateTokenStatus(JwtTokenEntity token) {
        token.setValid(false);
        this.jwtRepository.save(token);
    }
}
