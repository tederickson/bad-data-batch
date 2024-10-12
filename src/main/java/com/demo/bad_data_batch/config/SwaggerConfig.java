package com.demo.bad_data_batch.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI buildOpenAPI() {
        final var info = new Info();
        info.setTitle("Query Spring Batch API");
        info.setVersion("0.1.3");
        info.setDescription("REST API for Spring Batch application");
        return new OpenAPI().info(info);
    }

}
