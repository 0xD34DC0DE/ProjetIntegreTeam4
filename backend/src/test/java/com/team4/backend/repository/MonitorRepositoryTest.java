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
                Monitor.monitorBuilder().email("marcM@desjardin.com").password("marc123").isEnabled(true).build(),
                Monitor.monitorBuilder().email("johnnyJ@cae-tech.com").password("johnny123").isEnabled(false).build()
        );

        monitorRepository.saveAll(users).subscribe();
    }

    @Test
    void findByEmailAndIsEnabledTrue(){
        //ARRANGE
        String email1 = "marcM@desjardin.com";
        String email2 = "johnnyJ@cae-tech.com";
        String email3 = "inexistantEmail@gmail.com";

        //ACT
        Mono<Monitor> existingMonitorEnabledTrue = monitorRepository.findByEmailAndIsEnabledTrue(email1);
        Mono<Monitor> existingMonitorEnabledFalse = monitorRepository.findByEmailAndIsEnabledTrue(email2);
        Mono<Monitor> nonExistentMonitor = monitorRepository.findByEmailAndIsEnabledTrue(email3);

        //ASSERT
        StepVerifier.create(existingMonitorEnabledTrue)
                .assertNext(monitor -> assertEquals(email1,monitor.getEmail())).verifyComplete();
        StepVerifier.create(existingMonitorEnabledFalse).expectNextCount(0).verifyComplete();
        StepVerifier.create(nonExistentMonitor).expectNextCount(0).verifyComplete();

    }

}
