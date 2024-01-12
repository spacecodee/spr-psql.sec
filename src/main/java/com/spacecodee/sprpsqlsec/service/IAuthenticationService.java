package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.AuthenticationRequestVo;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;

public interface IAuthenticationService {
    RegisterUserDto registerOneCustomer(SaveUserVo newUser);

    AuthenticationResponsePojo login(AuthenticationRequestVo request);

    boolean validateToken(String jwt);
}
