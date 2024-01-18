package com.spacecodee.sprpsqlsec.service.impl;

import com.spacecodee.sprpsqlsec.data.dto.GrantedPermissionDto;
import com.spacecodee.sprpsqlsec.data.dto.ModuleDto;
import com.spacecodee.sprpsqlsec.data.dto.OperationDto;
import com.spacecodee.sprpsqlsec.data.dto.RoleDto;
import com.spacecodee.sprpsqlsec.persistence.repository.IRoleRepository;
import com.spacecodee.sprpsqlsec.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    @Value("${security.default.role}")
    private static final String DEFAULT_ROLE_NAME = "ROLE_CUSTOMER";

    @Override
    public Optional<RoleDto> findDefaultRole() {
        return this.roleRepository.findByName(DEFAULT_ROLE_NAME).map(roleEntity -> {
            var roleDto = new RoleDto();
            roleDto.setId(roleEntity.getId());
            roleDto.setName(roleEntity.getName());

            var permissions = new ArrayList<GrantedPermissionDto>();
            roleEntity.getPermissions().forEach(permission -> {
                var grantedPermissionDto = new GrantedPermissionDto();
                grantedPermissionDto.setId(permission.getId());
                grantedPermissionDto.setRoleId(roleDto);

                var operationDto = new OperationDto();
                operationDto.setId(permission.getOperationId().getId());
                operationDto.setTag(permission.getOperationId().getTag());
                operationDto.setPath(permission.getOperationId().getPath());
                operationDto.setHttpMethod(permission.getOperationId().getHttpMethod());
                operationDto.setPermitAll(permission.getOperationId().isPermitAll());

                var moduleDto = new ModuleDto();
                moduleDto.setId(permission.getOperationId().getModuleId().getId());
                moduleDto.setName(permission.getOperationId().getModuleId().getName());
                moduleDto.setBasePath(permission.getOperationId().getModuleId().getBasePath());

                operationDto.setModuleId(moduleDto);
                grantedPermissionDto.setOperationId(operationDto);

                permissions.add(grantedPermissionDto);
            });

            roleDto.setPermissions(permissions);
            return roleDto;
        });
    }
}
