package com.spacecodee.sprpsqlsec.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserEntityMapper {
    /*
    UserEntity toEntity(UDUserVo UDUserVo);

    UDUserVo toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UDUserVo UDUserVo, @MappingTarget UserEntity userEntity);*/
}