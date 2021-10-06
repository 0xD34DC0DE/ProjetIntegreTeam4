package com.team4.backend;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Order(1)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final MonitorRepository monitorRepository;

    private final InternshipOfferRepository internshipOfferRepository;

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public TestingInserterRunner(MonitorRepository monitorRepository, InternshipOfferRepository internshipOfferRepository, StudentRepository studentRepository, UserRepository userRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.monitorRepository = monitorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
        userRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();
        studentRepository.deleteAll().subscribe();
        internshipOfferRepository.deleteAll().subscribe();

        insertUsers();

        insertMonitors();

        String exclusiveOfferId =insertInternshipOffers();

        insertStudents(exclusiveOfferId);

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

    private void insertStudents(String exclusiveOfferId) {
        String password = pbkdf2Encoder.encode("student");
        Student student = Student.studentBuilder()
                .email("student@gmail.com")
                .password(password)
                .firstName("John")
                .lastName("Doe")
                .registrationDate(LocalDate.now())
                .studentState(StudentState.REGISTERED)
                .phoneNumber("123-123-1234")
                .exclusiveOffersId(Collections.singleton(exclusiveOfferId))
                .build();
        studentRepository.save(student).subscribe();
    }

    private String insertInternshipOffers() {
        InternshipOffer internshipOffer1 =
                InternshipOffer.builder()
                        .beginningDate(LocalDate.now().plusMonths(1))
                        .endingDate(LocalDate.now().plusMonths(2))
                        .limitDateToApply(LocalDate.now().plusDays(15))
                        .companyName("Company name")
                        .description("Description")
                        .isExclusive(false)
                        .maxSalary(17.50f)
                        .minSalary(16.25f)
                        .emailOfMonitor("9182738492@gmail.com")
                        .listEmailInterestedStudents(Collections.emptyList())
                        .build();

        InternshipOffer internshipOffer2 = InternshipOffer.builder()
                        .beginningDate(LocalDate.now().plusMonths(3))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .limitDateToApply(LocalDate.now().plusDays(11))
                        .companyName("Company name2")
                        .description("Description2")
                        .isExclusive(true)
                        .maxSalary(19.50f)
                        .minSalary(19.50f)
                        .emailOfMonitor("9182738492@gmail.com")
                        .listEmailInterestedStudents(Collections.emptyList())
                        .build();

        for (int i = 0; i < 4; i++) {
            internshipOffer1.setDescription(internshipOffer1.getDescription() + i);
            internshipOfferRepository.save(internshipOffer1).block();
            internshipOffer1.setId(null);
        }
        return internshipOfferRepository.save(internshipOffer2).block().getId();
    }

}
