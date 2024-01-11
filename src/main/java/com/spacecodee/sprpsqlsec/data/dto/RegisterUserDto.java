package com.spacecodee.sprpsqlsec.data.dto;

import com.spacecodee.sprpsqlsec.persistence.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */

@Data
public class RegisterUserDto implements Serializable {
    private long id;
    private String name;
    private String username;
    private String password;
    private String jwt;
    private String role;
}