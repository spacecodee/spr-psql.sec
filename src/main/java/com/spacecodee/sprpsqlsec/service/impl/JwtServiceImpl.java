package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.service.IJwtService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements IJwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private static long EXPIRATION_IN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private static String SECRET_KEY;

    @Override
    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        var issuedAt = new Date(System.currentTimeMillis());
        var expiration = new Date((EXPIRATION_IN_MINUTES * 60 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(this.generateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private Key generateKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
