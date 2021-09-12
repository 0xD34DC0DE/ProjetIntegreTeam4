package com.team4.backend;

import com.team4.backend.model.ExamplePerson;
import com.team4.backend.model.User;
import com.team4.backend.repository.PersonRepository;
import com.team4.backend.repository.UserRepository;
import com.team4.backend.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Random;


@Order(1)
@Profile("test")
@Component
public class TestingRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TestingRunner.class);

    private final PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestingRunner(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {

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
    /*
    Random rnd = new Random();
    Flux.fromStream(Arrays.stream(names)
            .map(name ->
                    personRepository.save(
                            new ExamplePerson(null, name, rnd.nextInt(55))
                    )
            ))
            .subscribe(p -> log.info("new person created: {}", p.block()));
     */

        Flux.fromStream(Arrays.stream(names)
                .map(name -> userRepository.save(User.builder().registrationNumber("12390213").firstName(name).build())))
                .subscribe(p -> log.info("new user created: {}", p.block()));

    }
}
