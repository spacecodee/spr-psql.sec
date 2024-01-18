package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.RoleDto;
import com.spacecodee.sprpsqlsec.persistence.entity.security.RoleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRoleEntityMapper {
    RoleEntity toEntity(RoleDto roleDto);

    RoleDto toDto(RoleEntity roleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RoleEntity partialUpdate(RoleDto roleDto, @MappingTarget RoleEntity roleEntity);
}