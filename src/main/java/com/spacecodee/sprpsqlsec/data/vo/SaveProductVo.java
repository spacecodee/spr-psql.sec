package com.spacecodee.sprpsqlsec.data.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveProductVo implements Serializable {

    @NotBlank
    private String name;

    @DecimalMin("0.01")
    private BigDecimal price;

    @Min(1)
    private Long categoryId;
}
