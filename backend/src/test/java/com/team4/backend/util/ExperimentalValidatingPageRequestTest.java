package com.team4.backend.util;

import com.team4.backend.exception.InvalidPageRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExperimentalValidatingPageRequestTest {


    @Test
    void getPageRequestValid() {
        //ARRANGE

        // ACT
        assertDoesNotThrow(() ->
                ExperimentalValidatingPageRequest.getPageRequest(1, 1)
        ); // ASSERT
    }

    @Test
    void getPageRequestMonoValid() {
        //ARRANGE
        Mono<PageRequest> pageRequestMono;

        // ACT
        pageRequestMono = ExperimentalValidatingPageRequest.getPageRequestMono(1, 1);

        //ASSERT
        StepVerifier.create(pageRequestMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getPageRequestInvalidSize() {
        //ARRANGE

        // ACT
        assertThrows(InvalidPageRequestException.class ,() ->
                ExperimentalValidatingPageRequest.getPageRequest(1, 0)
        ); // ASSERT
    }

    @Test
    void getPageRequestInvalidPage() {
        //ARRANGE

        // ACT
        assertThrows(InvalidPageRequestException.class ,() ->
                ExperimentalValidatingPageRequest.getPageRequest(-1, 1)
        ); // ASSERT
    }

    @Test
    void getPageRequestInvalidNullSize() {
        //ARRANGE

        // ACT
        assertThrows(InvalidPageRequestException.class ,() ->
                ExperimentalValidatingPageRequest.getPageRequest(1, null)
        ); // ASSERT
    }

    @Test
    void getPageRequestInvalidNullPage() {
        //ARRANGE

        // ACT
        assertThrows(InvalidPageRequestException.class ,() ->
                ExperimentalValidatingPageRequest.getPageRequest(null, 1)
        ); // ASSERT
    }

    @Test
    void getPageRequestInvalidNullPageNullSize() {
        //ARRANGE

        // ACT
        assertThrows(InvalidPageRequestException.class ,() ->
                ExperimentalValidatingPageRequest.getPageRequest(null, null)
        ); // ASSERT
    }

    @Test
    void applyPagingValid() {
        //ARRANGE
        List<Integer> values = new ArrayList<>(){{add(1); add(2); add(3); add(4); add(5);}};

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, 1, 2);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();
    }

    @Test
    void applyPagingValidTruncate() {
        //ARRANGE
        List<Integer> values = new ArrayList<>(){{add(1); add(2); add(3); add(4); add(5);}};

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, 2, 2);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    void applyPagingValidEndOfList() {
        //ARRANGE
        List<Integer> values = new ArrayList<>(){{add(1); add(2); add(3); add(4); add(5);}};

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, 2, 3);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void applyPagingInvalid(){
        //ARRANGE
        List<Integer> values = new ArrayList<>(){{add(1); add(2); add(3); add(4); add(5);}};

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, -1, 1);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }

}
