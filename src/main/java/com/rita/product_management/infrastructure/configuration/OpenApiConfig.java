package com.rita.product_management.infrastructure.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @Bean
    public OpenAPI defineOpenApi() {
        Server developmentServer = new Server();
        developmentServer.setUrl("http://localhost:8080");
        developmentServer.setDescription("Local");

        Server productionServer = new Server();
        productionServer.setUrl("https://rita-product-management-763d144bcf95.herokuapp.com");
        productionServer.setDescription("Production");

        Contact myContact = new Contact();
        myContact.setName("Rita Ferreira");
        myContact.setEmail("rialf.ferreira@gmail.com");

        Info information = new Info()
                .title("Product Management System API")
                .version("1.0")
                .description("This API exposes endpoints to manage products system.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(developmentServer, productionServer));
    }

}
