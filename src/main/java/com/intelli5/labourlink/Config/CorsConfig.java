package com.intelli5.labourlink.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
                .allowedOrigins("http://localhost:3000") // Allow requests from your frontend domain
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
                .allowCredentials(true); // Allow sending cookies with the request
    }
}