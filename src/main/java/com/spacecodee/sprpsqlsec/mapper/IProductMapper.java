package com.spacecodee.sprpsqlsec.mapper;

import com.spacecodee.sprpsqlsec.data.dto.ProductDto;
import com.spacecodee.sprpsqlsec.data.vo.SaveProductVo;
import com.spacecodee.sprpsqlsec.enums.ProductStatus;
import com.spacecodee.sprpsqlsec.persistence.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ICategoryMapper.class})
public interface IProductMapper {

    //Show Data
    @Mapping(target = "category", source = "categoryEntity")
    ProductDto toDto(ProductEntity productEntity);

    //Data modification
    @Mapping(target = "status", source = ".", qualifiedByName = "status")
    @Mapping(target = "categoryEntity", source = "categoryId", qualifiedByName = "setOnlyId")
    ProductEntity toEntity(SaveProductVo saveProductVo);

    @Named("status")
    default ProductStatus status(SaveProductVo saveProductVo) {
        return ProductStatus.ENABLED;
    }

    default ProductEntity disableStatus(ProductEntity productEntityFromDb) {
        productEntityFromDb.setStatus(ProductStatus.DISABLED);
        return productEntityFromDb;
    }

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "categoryEntity", source = "categoryId", qualifiedByName = "setOnlyId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(SaveProductVo saveProductVo, @MappingTarget ProductEntity productEntity);
}