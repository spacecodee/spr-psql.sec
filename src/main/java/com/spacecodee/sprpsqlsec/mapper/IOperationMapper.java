package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.OperationDto;
import com.spacecodee.sprpsqlsec.persistence.entity.security.OperationEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IOperationMapper {
    OperationEntity toEntity(OperationDto operationDto);

    OperationDto toDto(OperationEntity operationEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OperationEntity partialUpdate(OperationDto operationDto, @MappingTarget OperationEntity operationEntity);
}