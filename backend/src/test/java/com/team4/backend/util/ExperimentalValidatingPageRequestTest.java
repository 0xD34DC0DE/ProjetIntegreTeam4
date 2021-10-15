package com.team4.backend.util;

import com.team4.backend.exception.InvalidPageRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExperimentalValidatingPageRequestTest {

    @Test
    void getPageRequestValid() {
        // ASSERT
        assertDoesNotThrow(() ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(1, 1)
        );
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
        // ASSERT
        assertThrows(InvalidPageRequestException.class, () ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(1, 0)
        );
    }

    @Test
    void getPageRequestInvalidPage() {
        // ASSERT
        assertThrows(InvalidPageRequestException.class, () ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(-1, 1)
        );
    }

    @Test
    void getPageRequestInvalidNullSize() {
        // ASSERT
        assertThrows(InvalidPageRequestException.class, () ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(1, null)
        );
    }

    @Test
    void getPageRequestInvalidNullPage() {
        // ASSERT
        assertThrows(InvalidPageRequestException.class, () ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(null, 1)
        );
    }

    @Test
    void getPageRequestInvalidNullPageNullSize() {
        // ASSERT
        assertThrows(InvalidPageRequestException.class, () ->
                // ACT
                ExperimentalValidatingPageRequest.getPageRequest(null, null)
        );
    }

    @Test
    void applyPagingValid() {
        //ARRANGE
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

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
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

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
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, 2, 3);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void applyPagingInvalid() {
        //ARRANGE
        List<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        // ACT
        Flux<Integer> pagedFlux = ExperimentalValidatingPageRequest.applyPaging(values, -1, 1);

        // ASSERT
        StepVerifier.create(pagedFlux)
                .expectError(InvalidPageRequestException.class)
                .verify();
    }
}
