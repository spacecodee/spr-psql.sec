package com.spacecodee.sprpsqlsec.data.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveCategoryVo implements Serializable {

    @NotBlank
    private String name;
}
