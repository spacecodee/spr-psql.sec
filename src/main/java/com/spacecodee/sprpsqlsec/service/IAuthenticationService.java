package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;

public interface IAuthenticationService {
    RegisterUserDto registerOneCustomer(SaveUserVo newUser);
}
