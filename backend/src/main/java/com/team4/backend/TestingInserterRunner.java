package com.team4.backend;

import com.team4.backend.model.ExamplePerson;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.Role;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.repository.PersonRepository;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Order(1)
public class TestingInserterRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingInserterRunner.class);

    private final PersonRepository personRepository;

    private final MonitorRepository monitorRepository;

    private final UserRepository userRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public TestingInserterRunner(PersonRepository personRepository, MonitorRepository monitorRepository, UserRepository userRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.personRepository = personRepository;
        this.monitorRepository = monitorRepository;
        this.userRepository = userRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
        userRepository.deleteAll().subscribe();
        monitorRepository.deleteAll().subscribe();

        personRepository.deleteAll().block();
        String[] names = new String[]{
                "Sallyann Durlinga",
                "Rosco Darrel",
                "Barney Lindelof",
                "Aldric Garrison",
                "Bee Flipek",
                "Ginnifer Gaber",
                "Aubine Rraundl",
                "Ofelia Moorrud",
                "Kitti Meert",
                "Dona Biers",
                "Regina Hardisty",
                "Karine Brinkler",
                "Sabine Vaskov",
                "Errick Sparway",
                "Margarita Louiset",
                "Tadio Di Franceshci",
                "Fonsie Tedstone",
                "Maxine Lghan",
                "Gwennie Grewcock",
                "Wilona Frohock"
        };

        Random rnd = new Random();
        Flux.fromStream(Arrays.stream(names)
                .map(name ->
                        personRepository.save(
                                new ExamplePerson(null, name, rnd.nextInt(55))
                        )
                ))
                .subscribe(p -> log.info("new person created: {}", p.block()));

        insertUsers();

        insertMonitors();

    }

    private void insertUsers() {

        List<User> users = Arrays.asList(
                User.builder().email("123456789@gmail.com").role(Role.STUDENT).password(pbkdf2Encoder.encode("massou123")).isEnabled(true).build(),
                User.builder().email("45673234@gmail.com").role(Role.SUPERVISOR).password(pbkdf2Encoder.encode("sasuke123")).isEnabled(true).build(),
                User.builder().email("francoisLacoursiere@gmail.com").role(Role.INTERNSHIP_MANAGER).password(pbkdf2Encoder.encode("francois123")).isEnabled(true).build()
        );

        userRepository.saveAll(users).subscribe(u -> log.info("new user created: {}", u));

    }

    private void insertMonitors() {
        Monitor monitor = Monitor.monitorBuilder().email("marcAndre@desjardins.com").role(Role.MONITOR).password(pbkdf2Encoder.encode("marc123")).isEnabled(true).build();

        userRepository.save(monitor).subscribe(user -> log.info("User has been saved: {}", user));
        monitorRepository.save(monitor).subscribe(user -> log.info("Monitor has been saved: {}", user));
    }
}
