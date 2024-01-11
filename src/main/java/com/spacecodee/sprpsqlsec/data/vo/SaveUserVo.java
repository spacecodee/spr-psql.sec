package com.spacecodee.sprpsqlsec.data.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.UserEntity}
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