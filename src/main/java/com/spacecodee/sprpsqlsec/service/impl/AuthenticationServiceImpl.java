package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.AuthenticationRequestVo;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.service.IAuthenticationService;
import com.spacecodee.sprpsqlsec.service.IJwtService;
import com.spacecodee.sprpsqlsec.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterUserDto registerOneCustomer(SaveUserVo newUser) {
        UDUserVo user = userService.registerOneCustomer(newUser);

        var userDto = new RegisterUserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().name());

        var jwt = this.jwtService.generateToken(user, this.generateExtraClaims(user));
        userDto.setJwt(jwt);
        return userDto;
    }

    @Override
    public AuthenticationResponsePojo login(AuthenticationRequestVo request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        this.authenticationManager.authenticate(authentication);

        var userD = this.userService.findOneByUsername(request.getUsername()).get();

        String jwt = this.jwtService.generateToken(userD, this.generateExtraClaims(userD));

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
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public UDUserVo findLoggedInUser() {
        var auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        var username = auth.getPrincipal().toString();
        return this.userService.findOneByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User isn't found with Username: " + username + "."));
    }

    private Map<String, Object> generateExtraClaims(UDUserVo user) {
        return Map.of(
                "name", user.getName(),
                "role", user.getRole().name(),
                "authorities", user.getAuthorities()
        );
    }
}
