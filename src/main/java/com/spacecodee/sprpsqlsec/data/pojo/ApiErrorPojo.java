package com.spacecodee.sprpsqlsec.data.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ApiErrorPojo implements Serializable {

    private String backendMessage;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private String method;
}
