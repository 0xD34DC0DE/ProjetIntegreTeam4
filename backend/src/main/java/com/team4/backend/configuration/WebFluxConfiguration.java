package com.team4.backend.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

//@Configuration
//public class WebFluxConfiguration {
//
//    @Bean
//    public WebFluxConfigurer corsConfigurer() {
//        return new WebFluxConfigurerComposite() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*")
//                        .allowedMethods("*");
//            }
//        };
//    }
//
//}
