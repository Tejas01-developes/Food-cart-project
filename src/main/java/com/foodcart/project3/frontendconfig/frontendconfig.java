package com.foodcart.project3.frontendconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class frontendconfig {
@Bean
    public WebMvcConfigurer configurer(){
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                    .exposedHeaders("*")
                    .allowCredentials(true);


        }
    };
    }
    }
