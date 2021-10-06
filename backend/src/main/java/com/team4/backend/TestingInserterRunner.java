package com.team4.backend;

import com.team4.backend.model.*;
import com.team4.backend.model.enums.Role;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.SupervisorRepository;
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

    private final SupervisorRepository supervisorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final UserRepository userRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public TestingInserterRunner(MonitorRepository monitorRepository, InternshipOfferRepository internshipOfferRepository, UserRepository userRepository, SupervisorRepository supervisorRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.userRepository = userRepository;
        this.supervisorRepository = supervisorRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
        userRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        supervisorRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertUsers();
    }

    private void insertUsers() {
        List<User> users = Arrays.asList(
                Student.studentBuilder().email("123456789@gmail.com").firstName("Travis").lastName("Scott").phoneNumber("4387650987").password(pbkdf2Encoder.encode("massou123")).build(),
                InternshipManager.internshipManagerBuilder().email("francoisLacoursiere@gmail.com").password(pbkdf2Encoder.encode("francois123")).build(),
                Monitor.monitorBuilder().email("9182738492@gmail.com").password(pbkdf2Encoder.encode("lao@dkv23")).build(),
                Supervisor.supervisorBuilder().email("45673234@gmail.com").password(pbkdf2Encoder.encode("sasuke123")).build()
        );

        userRepository.saveAll(users).subscribe(u -> log.info("New users created: {}", u));
    }

}
