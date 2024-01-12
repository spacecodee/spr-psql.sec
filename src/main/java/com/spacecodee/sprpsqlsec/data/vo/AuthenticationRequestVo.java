package com.spacecodee.sprpsqlsec.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthenticationRequestVo implements Serializable {

    private String username;
    private String password;
}
