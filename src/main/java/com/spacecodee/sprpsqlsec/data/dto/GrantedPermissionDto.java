package com.spacecodee.sprpsqlsec.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.security.GrantedPermissionEntity}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantedPermissionDto implements Serializable {
    private int id;
    private RoleDto roleId;
    private OperationDto operationId;
}