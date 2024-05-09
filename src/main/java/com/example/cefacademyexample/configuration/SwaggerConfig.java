package com.example.cefacademyexample.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "User api", version = "v1", description = "Documentation of User API"))
public class SwaggerConfig {
}
