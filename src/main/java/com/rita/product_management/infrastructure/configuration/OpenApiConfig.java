package com.rita.product_management.infrastructure.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi securedApi() {
        return GroupedOpenApi.builder()
                .group("secured")
                .pathsToExclude("/auth/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("Bearer Authentication"));
                    return operation;
                })
                .build();
    }

}
