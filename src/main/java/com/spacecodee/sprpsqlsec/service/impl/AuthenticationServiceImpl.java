package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.AuthenticationRequestVo;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.persistence.entity.JwtTokenEntity;
import com.spacecodee.sprpsqlsec.persistence.entity.security.RoleEntity;
import com.spacecodee.sprpsqlsec.persistence.entity.security.UserEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IJwtTokenRepository;
import com.spacecodee.sprpsqlsec.service.IAuthenticationService;
import com.spacecodee.sprpsqlsec.service.IJwtService;
import com.spacecodee.sprpsqlsec.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenRepository jwtRepository;

    @Override
    public RegisterUserDto registerOneCustomer(SaveUserVo newUser) {
        UDUserVo user = this.userService.registerOneCustomer(newUser);
        var jwt = this.jwtService.generateToken(user, this.generateExtraClaims(user));

        this.saveUserToken(jwt, user);

        var userDto = new RegisterUserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().getName());

        userDto.setJwt(jwt);
        return userDto;
    }

    private void saveUserToken(String jwt, UDUserVo user) {
        JwtTokenEntity token = new JwtTokenEntity();
        token.setToken(jwt);

        var uEntity = new UserEntity();
        uEntity.setId(user.getId());
        uEntity.setName(user.getName());
        uEntity.setUsername(user.getUsername());
        uEntity.setPassword(user.getPassword());

        var rEntity = new RoleEntity();
        rEntity.setId(user.getRole().getId());
        rEntity.setName(user.getRole().getName());

        uEntity.setRole(rEntity);

        token.setUser(uEntity);
        token.setExpiryDate(this.jwtService.extractExpiration(jwt));
        token.setValid(true);

        this.jwtRepository.save(token);
    }

    @Override
    public AuthenticationResponsePojo login(AuthenticationRequestVo request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        this.authenticationManager.authenticate(authentication);

        var userD = this.userService.findOneByUsername(request.getUsername()).get();

        String jwt = this.jwtService.generateToken(userD, this.generateExtraClaims(userD));

        this.saveUserToken(jwt, userD);
        AuthenticationResponsePojo rsp = new AuthenticationResponsePojo();
        rsp.setJwt(jwt);

        return rsp;
    }

    @Override
    public boolean validateToken(String jwt) {

        try {
            this.jwtService.extractUsername(jwt);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UDUserVo findLoggedInUser() {
        var auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        var username = auth.getPrincipal().toString();
        return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User isn't found with Username: " + username + "."));
    }

    @Override
    public void logout(HttpServletRequest request) {
        var jwt = this.jwtService.extractJwtFromRequest(request);
        if (jwt == null || !StringUtils.hasText(jwt)) return;

        Optional<JwtTokenEntity> token = this.jwtRepository.findByToken(jwt);
        if (token.isPresent() && token.get().isValid()) {
            token.get().setValid(true);
            this.jwtRepository.save(token.get());
        }
    }

    private Map<String, Object> generateExtraClaims(UDUserVo user) {
        return Map.of(
                "name", user.getName(),
                "role", user.getRole().getName(),
                "authorities", user.getAuthorities()
        );
    }
}
