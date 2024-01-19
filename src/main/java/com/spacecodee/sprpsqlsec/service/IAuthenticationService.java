package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.pojo.AuthenticationResponsePojo;
import com.spacecodee.sprpsqlsec.data.vo.AuthenticationRequestVo;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    RegisterUserDto registerOneCustomer(SaveUserVo newUser);

    AuthenticationResponsePojo login(AuthenticationRequestVo request);

    boolean validateToken(String jwt);

    UDUserVo findLoggedInUser();

    void logout(HttpServletRequest request);
}
