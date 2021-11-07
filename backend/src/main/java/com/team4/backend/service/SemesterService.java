package com.team4.backend.service;

import com.team4.backend.dto.SemesterDto;
import com.team4.backend.exception.SemesterNotFoundException;
import com.team4.backend.model.Semester;
import com.team4.backend.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public Flux<Semester> initializeSemestersAnnually(List<Semester> semesters) {
        return semesterRepository.saveAll(semesters);
    }

    public Mono<Semester> findByFullName(String fullName) {
        return semesterRepository.findByFullName(fullName)
                .switchIfEmpty(Mono.error(new SemesterNotFoundException("Can't find semester " + fullName)));
    }

    public Mono<String> getCurrentSemesterFullName() {
        LocalDateTime currentDate = LocalDateTime.now();

        return semesterRepository.findByFromLessThanEqualAndToGreaterThanEqual(currentDate, currentDate)
                .map(Semester::getFullName)
                //This should never happen, because we create the 3 semester yearly, but in case it happens
                .switchIfEmpty(Mono.error(new SemesterNotFoundException("Can't find current semester!")));
    }

    public Mono<SemesterDto> getListSemesterFullName() {
        SemesterDto semesterDto = new SemesterDto();

        return getCurrentSemesterFullName().map(fullName -> {
            semesterDto.setCurrentSemesterFullName(fullName);
            return semesterDto;
        }).map(semester -> {
            semesterRepository.findAll().map(Semester::getFullName)
                    .subscribe(fullName -> semester.getSemestersFullNames().add(fullName));
            return semester;
        });
    }

}
