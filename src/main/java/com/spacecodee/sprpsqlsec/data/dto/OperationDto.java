package com.spacecodee.sprpsqlsec.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.spacecodee.sprpsqlsec.persistence.entity.security.OperationEntity}
 */
@Data
public class OperationDto implements Serializable {
    private int id;
    private String tag;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    private ModuleDto moduleId;
}