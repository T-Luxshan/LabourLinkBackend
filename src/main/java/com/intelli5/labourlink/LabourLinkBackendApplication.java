package com.intelli5.labourlink;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
public class LabourLinkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabourLinkBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*") // Adjust the mapping pattern as needed
                        .allowedOrigins("*") // Allow requests from this origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*"); // Allowed headers
            }
        };
    }

//    @Bean
//    FirebaseMessaging firebaseMessaging(){
//        GoogleCredentials googleCredentials=GoogleCredentials.fromStream(
//                new ClassPathResource("").getInputStream()
//        )
//    }
@PostConstruct
public void init() {
    // Setting Spring Boot SetTimeZone
    TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // or TimeZone.getDefault()
}
}
