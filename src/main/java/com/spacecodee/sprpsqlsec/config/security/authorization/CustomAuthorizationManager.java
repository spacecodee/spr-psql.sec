package com.spacecodee.sprpsqlsec.config.security.authorization;

import com.spacecodee.sprpsqlsec.persistence.entity.security.OperationEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IOperationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final IOperationRepository operationRepository;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext object) {

        HttpServletRequest request = object.getRequest();
        var url = this.extractUrl(request);
        var httpMethod = request.getMethod();

        var isPublic = this.isPublic(url, httpMethod);

        return new AuthorizationDecision(isPublic);
    }

    private boolean isPublic(String url, String httpMethod) {
        List<OperationEntity> publicAccessEndpoints = this.operationRepository.findByPublicAccess();

        return publicAccessEndpoints.stream()
                .anyMatch(operation -> {
                    var basePath = operation.getModuleId().getBasePath();
                    var pattern = Pattern.compile(basePath.concat(operation.getPath()));
                    var matcher = pattern.matcher(url);

                    return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
                });
    }

    private String extractUrl(HttpServletRequest request) {
        var contextPath = request.getContextPath();
        var url = request.getRequestURI();
        url = url.replaceFirst(contextPath, "");

        return url;
    }
}
