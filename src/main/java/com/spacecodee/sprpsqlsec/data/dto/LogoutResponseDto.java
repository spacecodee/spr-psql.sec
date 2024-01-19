package com.spacecodee.sprpsqlsec.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogoutResponseDto implements Serializable {

    private String message;
}
