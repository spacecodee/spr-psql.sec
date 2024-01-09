package com.spacecodee.sprpsqlsec.data.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ApiResponseDataPojo<E> {

    private String message;
    private E data;
    @Setter(AccessLevel.PRIVATE)
    private String status;
    @Setter(AccessLevel.PRIVATE)
    private int statusCode;
    @Setter(AccessLevel.PRIVATE)
    private LocalDate localDate = LocalDate.now();

    public void setHttpStatus(@NotNull HttpStatus status) {
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
    }
}
