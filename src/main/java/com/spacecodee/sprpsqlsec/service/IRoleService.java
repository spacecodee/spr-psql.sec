package com.spacecodee.sprpsqlsec.service;

import com.spacecodee.sprpsqlsec.data.dto.RoleDto;

import java.util.Optional;

public interface IRoleService {
    Optional<RoleDto> findDefaultRole();
}
