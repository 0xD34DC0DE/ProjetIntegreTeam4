package com.team4.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.service.InternshipManagerService;
import com.team4.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Component
@Order(1)
public class InternshipManagerRegistration implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(InternshipManagerRegistration.class);

    private final UserService userService;

    private final InternshipManagerService internshipManagerService;

    private final ResourceLoader resourceLoader;

    InternshipManagerRegistration(UserService userService, InternshipManagerService internshipManagerService, ResourceLoader resourceLoader) {
        this.userService = userService;
        this.internshipManagerService = internshipManagerService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(final ApplicationArguments args) {
        Resource resource = resourceLoader.getResource("classpath:internship_managers.json");
        ObjectMapper mapper = new ObjectMapper();

        try {

            List<InternshipManager> managers = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });

            managers.forEach(internshipManager -> {

                Boolean managerAlreadyExists = userService.isEmailTaken(internshipManager.getEmail()).block();

                if (managerAlreadyExists == null) {
                    throw new RuntimeException(
                            "isEmailTaken returned null during automatic internship manager creation"
                    );
                }

                if (!managerAlreadyExists) {
                    internshipManagerService.register(internshipManager).subscribe(result -> {
                        if (result.getId() == null) {
                            throw new RuntimeException("Failed to automatically create internship managers");
                        }
                    });
                }
            });

        } catch (IOException e) {
            throw new RuntimeException("Failed to read internship_managers.json for automatic insertion");
        }
    }
}