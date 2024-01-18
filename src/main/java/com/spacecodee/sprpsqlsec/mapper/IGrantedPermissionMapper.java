package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.GrantedPermissionDto;
import com.spacecodee.sprpsqlsec.persistence.entity.security.GrantedPermissionEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IGrantedPermissionMapper {
    GrantedPermissionEntity toEntity(GrantedPermissionDto grantedPermissionDto);

    GrantedPermissionDto toDto(GrantedPermissionEntity grantedPermissionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GrantedPermissionEntity partialUpdate(GrantedPermissionDto grantedPermissionDto, @MappingTarget GrantedPermissionEntity grantedPermissionEntity);
}