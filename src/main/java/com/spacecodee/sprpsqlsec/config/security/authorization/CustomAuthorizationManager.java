package com.spacecodee.sprpsqlsec.config.security.authorization;

import com.spacecodee.sprpsqlsec.data.dto.GrantedPermissionDto;
import com.spacecodee.sprpsqlsec.data.dto.OperationDto;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.persistence.entity.security.ModuleEntity;
import com.spacecodee.sprpsqlsec.persistence.entity.security.OperationEntity;
import com.spacecodee.sprpsqlsec.persistence.repository.IOperationRepository;
import com.spacecodee.sprpsqlsec.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final IOperationRepository operationRepository;
    private final IUserService userService;

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

        if (isPublic) {
            return new AuthorizationDecision(true);
        }

        var isGranted = this.isGranted(authentication.get(), url, httpMethod);
        return new AuthorizationDecision(isGranted);
    }

    private boolean isGranted(Authentication authentication, String url, String httpMethod) {
        if (authentication == null || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("Authentication credentials aren't found.");
        }

        List<OperationEntity> operations = this.obtainOperations(authentication);
        var isGranted = operations.stream()
                .anyMatch(getOperationDtoPredicate(url, httpMethod));

        return isGranted;
    }

    private List<OperationEntity> obtainOperations(Authentication authentication) {
        var authToken = (UsernamePasswordAuthenticationToken) authentication;
        var username = authToken.getPrincipal().toString();
        var user = this.userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User isn't found with Username: " + username + "."));

        var granters = user.getRole().getPermissions().stream()
                .map(GrantedPermissionDto::getOperationId).toList();

        var operationEntities = new ArrayList<OperationEntity>();

        for (OperationDto granter : granters) {
            var operation = new OperationEntity();
            operation.setId(granter.getId());
            operation.setTag(granter.getTag());
            operation.setPermitAll(granter.isPermitAll());
            operation.setPath(granter.getPath());
            operation.setHttpMethod(granter.getHttpMethod());

            var module = new ModuleEntity();
            module.setId(granter.getModuleId().getId());
            module.setName(granter.getModuleId().getName());
            module.setBasePath(granter.getModuleId().getBasePath());

            operation.setModuleId(module);

            operationEntities.add(operation);
        }

        return operationEntities;
    }

    private boolean isPublic(String url, String httpMethod) {
        List<OperationEntity> publicAccessEndpoints = this.operationRepository.findByPublicAccess();

        return publicAccessEndpoints.stream()
                .anyMatch(this.getOperationDtoPredicate(url, httpMethod));
    }

    private static Predicate<OperationEntity> getOperationDtoPredicate(String url, String httpMethod) {
        return operation -> {
            var basePath = operation.getModuleId().getBasePath();
            var pattern = Pattern.compile(basePath.concat(operation.getPath()));
            var matcher = pattern.matcher(url);

            return matcher.matches() && operation.getHttpMethod().equals(httpMethod);
        };
    }

    private String extractUrl(HttpServletRequest request) {
        var contextPath = request.getContextPath();
        var url = request.getRequestURI();
        url = url.replaceFirst(contextPath, "");

        return url;
    }
}
