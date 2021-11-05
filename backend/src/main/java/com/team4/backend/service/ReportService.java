package com.team4.backend.service;

import com.team4.backend.pdf.OffersPdf;
import com.team4.backend.pdf.StudentsPdf;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class ReportService {

    private final InternshipOfferService internshipOfferService;

    private final StudentService studentService;

    private final PdfService pdfService;

    private final int WINTER = 1;
    private final int SUMMER = 2;
    private final int FALL = 3;

    private final int JANUARY = 1;
    private final int MAY = 5;
    private final int JUNE = 6;
    private final int AUGUST = 8;
    private final int SEPTEMBER = 9;
    private final int DECEMBER = 12;

    private final int FIRST = 1;
    private final int THIRTY = 30;
    private final int THIRTY_FIRST = 31;


    public ReportService(InternshipOfferService internshipOfferService, StudentService studentService, PdfService pdfService) {
        this.internshipOfferService = internshipOfferService;
        this.studentService = studentService;
        this.pdfService = pdfService;
    }


    public Mono<byte[]> generateAllNonValidatedOffersReport(Integer sessionNumber) {
        List<Date> dates = calculateDates(sessionNumber);
        return internshipOfferService.getAllNonValidatedOffers(dates.get(0), dates.get(1)).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages non validées");
                    variables.put("dates", calculateLocalDates(sessionNumber));
                    return pdfService.renderPdf(new OffersPdf(variables));
                });
    }

    public Mono<byte[]> generateAllValidatedOffersReport(Integer sessionNumber) {
        List<Date> dates = calculateDates(sessionNumber);
        return internshipOfferService.getAllValidatedOffers(dates.get(0), dates.get(1)).collectList()
                .flatMap(validatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", validatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages validées");
                    variables.put("dates", calculateLocalDates(sessionNumber));
                    return pdfService.renderPdf(new OffersPdf(variables));
                });
    }

    public Mono<byte[]> generateAllStudentsReport() {
        return studentService.getAll().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants inscrits");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsNoCvReport() {
        return studentService.getAllStudentsWithNoCv().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants sans CV");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsUnvalidatedCvReport() {
        return studentService.getAllStudentsWithUnvalidatedCv().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants dont le CV n'est pas validé");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsNoInternshipReport() {
        return studentService.getStudentsNoInternship().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants n'ayant aucune covocation à une entrevue");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsWaitingInterviewReport() {
        return studentService.getStudentsWaitingInterview().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants en attente d'entrevue");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsWaitingInterviewResponseReport() {
        return studentService.getStudentsWaitingResponse().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants en attente d'une réponse d'entrevue");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsWithInternshipReport() {
        return studentService.getStudentsWithInternship().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants qui ont trouvé un stage");
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    public Mono<byte[]> generateStudentsNotEvaluatedReport(Integer sessionNumber) {
        List<LocalDate> dates = calculateLocalDates(sessionNumber);
        return studentService.getAllWithEvaluationDateBetween(dates.get(0), dates.get(1)).collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants qui n'ont pas encore été évalués par leur moniteur");
                    variables.put("dates", calculateLocalDates(sessionNumber));
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

    protected List<Date> calculateDates(Integer sessionNumber) {
        List<Date> dates = new ArrayList<>();

        String season = sessionNumber.toString().substring(0, 1);
        String year = 20 + sessionNumber.toString().substring(1, 3);

        Date startDate = new Date();
        Date endDate = new Date();
        if (Integer.parseInt(season) == WINTER) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), JANUARY, FIRST);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), MAY, THIRTY_FIRST);
            endDate = convertLocalDateToDate(endLocalDate);
        } else if (Integer.parseInt(season) == SUMMER) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), JUNE, FIRST);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), AUGUST, THIRTY);
            endDate = convertLocalDateToDate(endLocalDate);
        } else if (Integer.parseInt(season) == FALL) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), SEPTEMBER, FIRST);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), DECEMBER, THIRTY_FIRST);
            endDate = convertLocalDateToDate(endLocalDate);
        }

        dates.add(startDate);
        dates.add(endDate);

        return dates;
    }

    protected List<LocalDate> calculateLocalDates(Integer sessionNumber) {
        List<LocalDate> dates = new ArrayList<>();

        String season = sessionNumber.toString().substring(0, 1);
        String year = 20 + sessionNumber.toString().substring(1, 3);

        LocalDate startLocalDate = null;
        LocalDate endLocalDate = null;
        if (Integer.parseInt(season) == WINTER) {
            startLocalDate = LocalDate.of(Integer.parseInt(year), JANUARY, FIRST);
            endLocalDate = LocalDate.of(Integer.parseInt(year), MAY, THIRTY_FIRST);
        } else if (Integer.parseInt(season) == SUMMER) {
            startLocalDate = LocalDate.of(Integer.parseInt(year), JUNE, FIRST);
            endLocalDate = LocalDate.of(Integer.parseInt(year), AUGUST, THIRTY);
        } else if (Integer.parseInt(season) == FALL) {
            startLocalDate = LocalDate.of(Integer.parseInt(year), SEPTEMBER, FIRST);
            endLocalDate = LocalDate.of(Integer.parseInt(year), DECEMBER, THIRTY_FIRST);
        }

        dates.add(startLocalDate);
        dates.add(endLocalDate);

        return dates;
    }

    protected Date convertLocalDateToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

}
