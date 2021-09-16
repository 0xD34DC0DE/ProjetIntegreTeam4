package com.team4.backend.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperComponent {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
