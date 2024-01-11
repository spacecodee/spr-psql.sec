package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.service.IAuthenticationService;
import com.spacecodee.sprpsqlsec.service.IJwtService;
import com.spacecodee.sprpsqlsec.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserService userService;
    private final IJwtService jwtService;

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

    private Map<String, Object> generateExtraClaims(UDUserVo user) {
        return Map.of(
                "name", user.getName(),
                "role", user.getRole().name(),
                "authorities", user.getAuthorities()
        );
    }
}
