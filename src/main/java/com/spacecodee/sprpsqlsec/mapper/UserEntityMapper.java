package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.RegisterUserDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveUserVo;
import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.persistence.entity.UserEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {
    UserEntity toEntity(UDUserVo UDUserVo);

    UDUserVo toDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UDUserVo UDUserVo, @MappingTarget UserEntity userEntity);

    UserEntity toEntity(RegisterUserDto registerUserDto);

    RegisterUserDto toDto1(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(RegisterUserDto registerUserDto, @MappingTarget UserEntity userEntity);

    UserEntity toEntity(SaveUserVo saveUserVo);

    SaveUserVo toDto2(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(SaveUserVo saveUserVo, @MappingTarget UserEntity userEntity);
}