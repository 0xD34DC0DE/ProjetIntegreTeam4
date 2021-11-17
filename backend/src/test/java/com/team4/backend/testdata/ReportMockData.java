package com.team4.backend.testdata;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class ReportMockData {

    public static Flux<Student> getStudentsFlux() {
        return Flux.just(new Student());
    }

    public static Mono<Student> getStudentMono() { return  Mono.just(new Student()); }

    public static Flux<InternshipOffer> getInternshipOffers() {
        return Flux.just(new InternshipOffer());
    }

    public static Mono<byte[]> getMonoBytes() {
        return Mono.just("bytes".getBytes());
    }

    public static byte[] getBytes() {
        return "bytes".getBytes();
    }

    public static Mono<List<String>> getStudentsEmailMonoList() {
        return Mono.just(List.of("test1@email.com", "test2@email.com", "test3@email.com"));
    }

}
