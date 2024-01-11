package com.spacecodee.sprpsqlsec.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface IJwtService {
    String generateToken(UserDetails user, Map<String, Object> extraClaims);
}
