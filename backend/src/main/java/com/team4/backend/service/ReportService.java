package com.team4.backend.service;

import com.team4.backend.pdf.OffersPdf;
import com.team4.backend.pdf.StudentsPdf;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Dates;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
public class ReportService {

    private final InternshipOfferService internshipOfferService;

    private final StudentService studentService;

    private final PdfService pdfService;

    public ReportService(InternshipOfferService internshipOfferService, StudentService studentService, PdfService pdfService) {
        this.internshipOfferService = internshipOfferService;
        this.studentService = studentService;
        this.pdfService = pdfService;
    }

    public Mono<byte[]> generateAllNonValidatedOffersReport(Integer sessionNumber) {
        List<Date> dates = calculateDates(sessionNumber);
        // TODO: implémenter les dates de session
        return internshipOfferService.getAllNonValidatedOffers(dates.get(0), dates.get(1)).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages non validées");
                    return pdfService.renderPdf(new OffersPdf(variables));
                });
    }

    public Mono<byte[]> generateAllValidatedOffersReport() {
        // TODO: implémenter les dates de session
        return internshipOfferService.getAllValidatedOffers(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE)).collectList()
                .flatMap(validatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", validatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages validées");
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

    private List<Date> calculateDates(Integer sessionNumber) {
        List<Date> dates = new ArrayList<>();

        String season = sessionNumber.toString().substring(0, 1);
        String year = 20 + sessionNumber.toString().substring(1, 3);

        Date startDate = new Date();
        Date endDate = new Date();
        if (Integer.parseInt(season) == 1) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), 1, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), 5, 31);
            endDate = convertLocalDateToDate(endLocalDate);
            season = "Hiver";
        } else if (Integer.parseInt(season) == 2) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), 6, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), 8, 30);
            endDate = convertLocalDateToDate(endLocalDate);
            season = "Ete";
        } else if (Integer.parseInt(season) == 3) {
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(year), 8, 1);
            startDate = convertLocalDateToDate(startLocalDate);

            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(year), 12, 31);
            endDate = convertLocalDateToDate(endLocalDate);
            season = "Automne";
        }

        dates.add(startDate);
        dates.add(endDate);

        System.out.println("dates " + dates);

        return dates;
    }

    private Date convertLocalDateToDate(LocalDate localDate) {
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //local date + atStartOfDay() + default time zone + toInstant() = Date
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

}
