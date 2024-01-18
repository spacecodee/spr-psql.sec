package com.spacecodee.sprpsqlsec.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.security.RoleEntity}
 */
@Data
public class RoleDto implements Serializable {
    private int id;
    private String name;
    private List<GrantedPermissionDto> permissions;
}