package com.spacecodee.sprpsqlsec.data.vo;

import com.spacecodee.sprpsqlsec.persistence.entity.security.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link UserEntity}
 */
@Data
public class SaveUserVo implements Serializable {
    @Size(min = 4)
    @NotBlank
    private String name;
    @Size(min = 4)
    @NotBlank
    private String username;
    @Size(min = 8)
    @NotBlank
    private String password;
    @Size(min = 8)
    @NotBlank
    private String repeatedPassword;
}