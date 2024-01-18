package com.spacecodee.sprpsqlsec.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.security.ModuleEntity}
 */
@Data
public class ModuleDto implements Serializable {
    private int id;
    private String name;
    private String basePath;
}