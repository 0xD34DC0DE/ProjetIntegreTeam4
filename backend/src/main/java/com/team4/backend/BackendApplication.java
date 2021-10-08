package com.team4.backend;

import com.team4.backend.meta.ExcludeFromGeneratedCoverage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    @ExcludeFromGeneratedCoverage
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
