package com.team4.backend;

import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final MonitorRepository monitorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final UserRepository userRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public TestingInserterRunner(MonitorRepository monitorRepository, InternshipOfferRepository internshipOfferRepository, UserRepository userRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.userRepository = userRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
        userRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertUsers();

        insertMonitors();
    }

    private void insertUsers() {
        List<User> users = Arrays.asList(
                User.builder().email("123456789@gmail.com").firstName("Travis").lastName("Scott").phoneNumber("4387650987").role(Role.STUDENT).password(pbkdf2Encoder.encode("massou123")).isEnabled(true).build(),
                User.builder().email("45673234@gmail.com").role(Role.SUPERVISOR).password(pbkdf2Encoder.encode("sasuke123")).isEnabled(true).build(),
                User.builder().email("francoisLacoursiere@gmail.com").role(Role.INTERNSHIP_MANAGER).password(pbkdf2Encoder.encode("francois123")).isEnabled(true).build()
        );

        userRepository.saveAll(users).subscribe(u -> log.info("new user created: {}", u));
    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder()
                .email("9182738492@gmail.com").password(pbkdf2Encoder.encode("lao@dkv23")).build();

        monitorRepository.save(monitor).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }
}
