package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.Student;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.*;

public abstract class InternshipOfferMockData {

    public static InternshipOffer getInternshipOffer() {
        return InternshipOffer.builder()
                .id("id")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .emailOfMonitor("rickJones@desjardins.com")
                .description("Développeur Web")
                .listEmailInterestedStudents(getInterestedStudentsEmailList())
                .isValidated(true)
                .isExclusive(false)
                .build();
    }

    public static Flux<InternshipOffer> getAllInternshipOffers() {
        return Flux.just(InternshipOffer.builder()
                        .id("123abc4def56ghi")
                        .limitDateToApply(LocalDate.now().plusMonths(1))
                        .beginningDate(LocalDate.now().plusMonths(2))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .companyName("umaknow")
                        .emailOfMonitor("maxime@umaknow.com")
                        .description("Développeur Frontend")
                        .isValidated(true)
                        .isExclusive(true)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build(),
                InternshipOffer.builder()
                        .id("234abc2def54ghi")
                        .limitDateToApply(LocalDate.now().plusMonths(1))
                        .beginningDate(LocalDate.now().plusMonths(2))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .minSalary(20.0f)
                        .maxSalary(25.0f)
                        .companyName("CGI")
                        .emailOfMonitor("patrickNormand@cgi.com")
                        .description("Technicien en Informatique")
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build());
    }

    public static Flux<InternshipOffer> getNonValidatedInternshipOffers() {
        return Flux.just(InternshipOffer.builder()
                        .id("123abc4def56ghi")
                        .limitDateToApply(LocalDate.now().plusMonths(1))
                        .beginningDate(LocalDate.now().plusMonths(2))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .companyName("umaknow")
                        .emailOfMonitor("maxime@umaknow.com")
                        .description("Développeur Frontend")
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(true)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build(),
                InternshipOffer.builder()
                        .id("234abc2def54ghi")
                        .limitDateToApply(LocalDate.now().plusMonths(1))
                        .beginningDate(LocalDate.now().plusMonths(2))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .minSalary(20.0f)
                        .maxSalary(25.0f)
                        .companyName("CGI")
                        .emailOfMonitor("patrickNormand@cgi.com")
                        .description("Technicien en Informatique")
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build());
    }

    public static InternshipOfferDto getInternshipOfferDto() {
        return InternshipOfferDto.builder()
                .id("234dsd2egd54ter")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .description("Développeur Web")
                .build();
    }

    public static InternshipOfferCreationDto getInternshipOfferCreationDto() {
        return InternshipOfferCreationDto.internshipOfferCreationDtoBuilder()
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .emailOfMonitor("rickJones@desjardins.com")
                .description("Développeur Web")
                .build();
    }

    public static InternshipOfferStudentViewDto getInternshipStudentViewDto() {
        return InternshipOfferStudentViewDto.internshipOfferStudentViewDtoBuilder()
                .id("id")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .description("Développeur Web")
                .build();
    }

    public static Set<String> getInterestedStudentsEmailList() {
        Set<String> studentEmails = new HashSet<>();
        studentEmails.add("student1@email.com");
        studentEmails.add("student2@email.com");
        return studentEmails;
    }

    public static List<InternshipOffer> getListInternshipOffer(int count) {
        List<InternshipOffer> internshipOffers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            InternshipOffer internshipOffer = getInternshipOffer();

            internshipOffer.setId(internshipOffer.getId() + "_" + i);

            internshipOffers.add(internshipOffer);
        }

        return internshipOffers;
    }

    public static List<InternshipOfferStudentViewDto> getListInternshipOfferStudentViewDto(int count) {
        List<InternshipOfferStudentViewDto> internshipOffers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            InternshipOfferStudentViewDto internshipOfferStudentViewDto = getInternshipStudentViewDto();

            internshipOfferStudentViewDto.setId(internshipOfferStudentViewDto.getId() + "_" + i);
            internshipOfferStudentViewDto.setHasAlreadyApplied(false);

            internshipOffers.add(internshipOfferStudentViewDto);
        }

        return internshipOffers;
    }

}
