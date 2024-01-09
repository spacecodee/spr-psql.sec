package com.spacecodee.sprpsqlsec.data.dto;

import com.spacecodee.sprpsqlsec.enums.CategoryStatus;
import com.spacecodee.sprpsqlsec.persistence.entity.CategoryEntity;

import java.io.Serializable;

/**
 * DTO for {@link CategoryEntity}
 */
public record CategoryDto(long id, String name, CategoryStatus status) implements Serializable {
}