package com.team4.backend.repository;

import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {MonitorRepository.class})
public class MonitorRepositoryTest {

    @Autowired
    MonitorRepository monitorRepository;

    @BeforeAll
    void init() {

        Flux<Monitor> users = Flux.just(
                Monitor.monitorBuilder().email("marcM@desjardin.com").password("marc123").build(),
                Monitor.monitorBuilder().email("johnnyJ@cae-tech.com").password("johnny123").build()
        );

        monitorRepository.saveAll(users).subscribe();
    }

    @Test
    void shouldFindByEmail() {
        //ARRANGE
        String email = "marcM@desjardin.com";

        //ACT
        Mono<Monitor> monitorMono = monitorRepository.findByEmail(email);

        //ASSERT
        StepVerifier.create(monitorMono)
                .assertNext(monitor -> assertEquals(email, monitor.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindByEmail() {
        //ARRANGE
        String email = "inexistantEmail@gmail.com";

        //ACT
        Mono<Monitor> nonExistentMonitor = monitorRepository.findByEmail(email);

        //ACT
        StepVerifier.create(nonExistentMonitor)
                .expectNextCount(0)
                .verifyComplete();
    }

}
