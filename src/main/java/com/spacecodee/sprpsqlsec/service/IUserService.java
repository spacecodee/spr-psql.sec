package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;

import java.util.Optional;

public interface IUserService {
    UDUserVo registerOneCustomer(SaveUserVo newUser);

    Optional<UDUserVo> findOneByUsername(String username);
}
