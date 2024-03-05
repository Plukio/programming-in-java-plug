package com.harbourspace.tracker.config;

import com.harbourspace.tracker.authorization.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * No that this is not standard or common security configuration in Spring Boot. In real world, you would use
 * different authentication and authorization mechanisms, like JWT, OAuth, etc.
 * This is very simple and naive implementation, for the purpose of this exercise.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthorizationService authorizationService;

    public SecurityConfiguration(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthorizationManager<RequestAuthorizationContext> authorizationManager
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // should not be disabled in real world application
                .authorizeHttpRequests(request -> request
                        .anyRequest()
                        .access(authorizationManager)
                )
                .build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager() {
        return (authentication, context) -> {
            var authHeader = context.getRequest().getHeader("Authorization");
            var isGranted = authHeader.startsWith("Basic ");

            if (isGranted) {
                var userId = Long.parseLong(authHeader.replace("Basic ", ""));
                authorizationService.setSecurityContext(userId);
            }

            return new AuthorizationDecision(isGranted);
        };
    }

}
