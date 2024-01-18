package com.spacecodee.sprpsqlsec.config.security;

import com.spacecodee.sprpsqlsec.config.security.filter.JwtAuthenticationFilter;
import com.spacecodee.sprpsqlsec.enums.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint customAuthenticationEntryPoint;
    private final AccessDeniedHandler customAccessDeniedHandler;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider)
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().access(this.authorizationManager);
                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(this.customAuthenticationEntryPoint);
                    exception.accessDeniedHandler(this.customAccessDeniedHandler);
                })
                .build();
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        // Products
        authorize.requestMatchers(HttpMethod.GET, "/product")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

        authorize.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/product/[0-9]*"))
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.POST, "/product")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.PUT, "/product/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.PUT, "/product/{productId}/disabled")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name());

        //Categories
        authorize.requestMatchers(HttpMethod.GET, "/category")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.GET, "/category/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.POST, "/category")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.PUT, "/category/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        authorize.requestMatchers(HttpMethod.PUT, "/category/{productId}/disabled")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name());

        //Auth Profile
        authorize.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        //Auth Public
        authorize.requestMatchers(HttpMethod.POST, "/customer").permitAll();
        authorize.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authorize.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        //Auth
        authorize.anyRequest().authenticated();
    }

    private static void buildRequestMatchersV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        //Auth Public
        authorize.requestMatchers(HttpMethod.POST, "/customer").permitAll();
        authorize.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authorize.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        //Auth
        authorize.anyRequest().authenticated();
    }
}
