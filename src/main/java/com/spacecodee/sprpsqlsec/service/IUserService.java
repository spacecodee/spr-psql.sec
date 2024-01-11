package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;

public interface IUserService {
    UDUserVo registerOneCustomer(SaveUserVo newUser);
}
