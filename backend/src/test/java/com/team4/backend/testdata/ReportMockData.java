package com.team4.backend.testdata;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class ReportMockData {

    public static Flux<Student> getStudents() {
        return Flux.just(new Student());
    }

    public static Flux<InternshipOffer> getInternshipOffers() {
        return Flux.just(new InternshipOffer());
    }

    public static Mono<byte[]> getMonoBytes() {
        return Mono.just("bytes".getBytes());
    }

    public static byte[] getBytes() {
        return "bytes".getBytes();
    }

}
