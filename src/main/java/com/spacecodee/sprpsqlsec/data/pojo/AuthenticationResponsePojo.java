package com.spacecodee.sprpsqlsec.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AuthenticationResponsePojo implements Serializable {

    private String jwt;
}
