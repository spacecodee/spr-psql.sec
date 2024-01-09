package com.spacecodee.sprpsqlsec.data.dto;

import com.spacecodee.sprpsqlsec.enums.ProductStatus;
import com.spacecodee.sprpsqlsec.persistence.entity.ProductEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ProductEntity}
 */
public record ProductDto(long id, String name, BigDecimal price, ProductStatus status,
                         CategoryDto category) implements Serializable {
}