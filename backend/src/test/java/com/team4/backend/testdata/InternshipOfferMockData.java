package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDetailsDto;
import com.team4.backend.dto.InternshipOfferStudentInterestViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.model.InternshipOffer;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.ZoneId;
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
                .companyName("Desjardins")
                .monitorEmail("rickJones@desjardins.com")
                .title("Développeur Web")
                .description("Description de Desjardins")
                .listEmailInterestedStudents(getInterestedStudentsEmailList())
                .emailOfApprovingInternshipManager("manager@gmail.com")
                .isValidated(true)
                .isExclusive(false)
                .build();
    }

    public static InternshipOffer getFirstInternshipOffer() {
        return InternshipOffer.builder()
                .id("123abc4def56ghi")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(19.0f)
                .maxSalary(22.0f)
                .companyName("Umaknow")
                .monitorEmail("maxime@umaknow.com")
                .title("Développeur Web")
                .description("Description de Umaknow")
                .isValidated(true)
                .isExclusive(true)
                .listEmailInterestedStudents(getInterestedStudentsEmailList())
                .build();
    }

    public static InternshipOffer getSecondInternshipOffer() {
        return InternshipOffer.builder()
                .id("234abc2def54ghi")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(20.0f)
                .maxSalary(25.0f)
                .companyName("CGI")
                .monitorEmail("patrickNormand@cgi.com")
                .title("Technicien informatique")
                .description("Description de CGI")
                .isValidated(false)
                .isExclusive(false)
                .listEmailInterestedStudents(getInterestedStudentsEmailList())
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
                        .companyName("Umaknow")
                        .monitorEmail("maxime@umaknow.com")
                        .title("Développeur Web")
                        .description("Description de Umaknow")
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
                        .monitorEmail("patrickNormand@cgi.com")
                        .title("Technicien informatique")
                        .description("Description de CGI")
                        .isValidated(false)
                        .isExclusive(false)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build());
    }

    public static List<Date> getSessionDates() {
        final int JANUARY = 1;
        final int MAY = 5;
        final int JUNE = 6;
        final int AUGUST = 8;
        final int SEPTEMBER = 9;
        final int DECEMBER = 12;

        List<Date> dates = new ArrayList<>();

        String season = "3";
        String year = "2021";

        Date startDate = new Date();
        Date endDate = new Date();
        if (Integer.parseInt(season) == 1) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), JANUARY, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), MAY, 31);
            endDate = convertLocalDateToDate(endLocalDate);
        } else if (Integer.parseInt(season) == 2) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), JUNE, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), AUGUST, 30);
            endDate = convertLocalDateToDate(endLocalDate);
        } else if (Integer.parseInt(season) == 3) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), SEPTEMBER, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), DECEMBER, 31);
            endDate = convertLocalDateToDate(endLocalDate);
        }

        dates.add(startDate);
        dates.add(endDate);

        return dates;
    }

    private static Date convertLocalDateToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public static Flux<InternshipOffer> getNonValidatedInternshipOffers() {
        return Flux.just(InternshipOffer.builder()
                        .id("123abc4def56ghi")
                        .limitDateToApply(LocalDate.now().plusMonths(1))
                        .beginningDate(LocalDate.now().plusMonths(2))
                        .endingDate(LocalDate.now().plusMonths(6))
                        .minSalary(19.0f)
                        .maxSalary(22.0f)
                        .companyName("Umaknow")
                        .monitorEmail("maxime@umaknow.com")
                        .title("Développeur Web")
                        .description("Description de Umaknow")
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
                        .monitorEmail("patrickNormand@cgi.com")
                        .title("Technicien en Informatique")
                        .description("Description de CGI")
                        .isValidated(false)
                        .validationDate(null)
                        .isExclusive(false)
                        .listEmailInterestedStudents(getInterestedStudentsEmailList())
                        .build());
    }

    public static InternshipOfferDetailsDto getInternshipOfferDetailedDto() {
        return InternshipOfferDetailsDto.internshipOfferDetailedDtoBuilder()
                .id("234dsd2egd54ter")
                .title("Développeur Web")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("Desjardins")
                .description("Description de Desjardins")
                .build();
    }

    public static InternshipOfferCreationDto getInternshipOfferCreationDto() {
        return InternshipOfferCreationDto.internshipOfferCreationDtoBuilder()
                .title("Développeur Web")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("Desjardins")
                .monitorEmail("rickJones@desjardins.com")
                .description("Description de Desjardins")
                .build();
    }

    public static InternshipOfferStudentViewDto getInternshipStudentViewDto() {
        return InternshipOfferStudentViewDto.internshipOfferStudentViewDtoBuilder()
                .id("id")
                .title("Développeur Web")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("Desjardins")
                .description("Description de Desjardins")
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

    public static InternshipOfferStudentInterestViewDto getInternshipStudentInterestViewDto() {
        return InternshipOfferStudentInterestViewDto.internshipOfferStudentInterestViewDtoBuilder()
                .id("id")
                .title("title")
                .companyName("company")
                .description("description")
                .interestedStudentList(StudentMockData.getListStudent(2))
                .build();
    }

    public static List<InternshipOfferStudentInterestViewDto> getListInternshipOfferStudentInterestViewDto(int count) {
        List<InternshipOfferStudentInterestViewDto> internshipOffers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            InternshipOfferStudentInterestViewDto internshipOfferStudentInterestViewDto = getInternshipStudentInterestViewDto();

            internshipOfferStudentInterestViewDto.setId(internshipOfferStudentInterestViewDto.getId() + "_" + i);

            internshipOffers.add(internshipOfferStudentInterestViewDto);
        }

        return internshipOffers;
    }

}
