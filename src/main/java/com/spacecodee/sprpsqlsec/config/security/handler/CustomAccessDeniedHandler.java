package com.spacecodee.sprpsqlsec.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spacecodee.sprpsqlsec.data.pojo.ApiErrorPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ApiErrorPojo apiErrorPojo = new ApiErrorPojo();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        this.apiErrorPojo.setBackendMessage(accessDeniedException.getLocalizedMessage());
        this.apiErrorPojo.setMessage("Access denied, you don't have permission to access this resource, please contact the administrator for more information");
        this.apiErrorPojo.setTimestamp(LocalDateTime.now());
        this.apiErrorPojo.setPath(request.getRequestURI());
        this.apiErrorPojo.setMethod(request.getMethod());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        var apiErrorAsJson = objectMapper.writeValueAsString(this.apiErrorPojo);

        response.getWriter().write(apiErrorAsJson);
    }
}
