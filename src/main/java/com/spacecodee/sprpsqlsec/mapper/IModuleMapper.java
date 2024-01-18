package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.ModuleDto;
import com.spacecodee.sprpsqlsec.persistence.entity.security.ModuleEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IModuleMapper {
    ModuleEntity toEntity(ModuleDto moduleDto);

    ModuleDto toDto(ModuleEntity moduleEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ModuleEntity partialUpdate(ModuleDto moduleDto, @MappingTarget ModuleEntity moduleEntity);
}