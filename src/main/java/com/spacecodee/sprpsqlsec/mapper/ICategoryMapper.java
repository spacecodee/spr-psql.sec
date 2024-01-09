package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.CategoryDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveCategoryVo;
import com.spacecodee.sprpsqlsec.enums.CategoryStatus;
import com.spacecodee.sprpsqlsec.persistence.entity.CategoryEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ICategoryMapper {

    //Show Data
    CategoryDto toDto(CategoryEntity categoryEntity);

    //Data modification
    @Mapping(target = "status", source = ".", qualifiedByName = "status")
    CategoryEntity toEntity(SaveCategoryVo categoryDto);

    @Named("status")
    default CategoryStatus status(SaveCategoryVo saveCategoryVo) {
        return CategoryStatus.ENABLED;
    }

    @Named("setOnlyId")
    default CategoryEntity setOnlyId(Long categoryId) {
        var category = new CategoryEntity();
        category.setId(categoryId);
        return category;
    }

    default CategoryEntity disableStatus(CategoryEntity categoryEntity) {
        categoryEntity.setStatus(CategoryStatus.DISABLED);
        return categoryEntity;
    }

    @Mapping(target = "status", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CategoryEntity partialUpdate(SaveCategoryVo categoryDto, @MappingTarget CategoryEntity categoryEntity);
}