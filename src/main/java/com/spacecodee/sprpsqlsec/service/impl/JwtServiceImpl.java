package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date((this.EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(this.generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return this.extractAllClaims(jwt).getSubject();
    }

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") || !StringUtils.hasText(authorizationHeader)) {
            return null;
        }

        return authorizationHeader.split(" ")[1];
    }

    @Override
    public Date extractExpiration(String jwt) {
        return this.extractAllClaims(jwt).getExpiration();
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(this.generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private SecretKey generateKey() {
        var passDecoder = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(passDecoder);
    }
}
