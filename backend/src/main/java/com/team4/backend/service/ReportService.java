package com.team4.backend.service;

import com.team4.backend.pdf.OffersPdf;
import com.team4.backend.pdf.StudentsPdf;
import com.team4.backend.util.SemesterUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


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

    public Mono<byte[]> generateAllNonValidatedOffersReport(String semesterFullName) {
        return internshipOfferService.getAllNonValidatedOffers(semesterFullName).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages non validées");
                    variables.put("session", SemesterUtil.convertInFrench(semesterFullName));
                    return pdfService.renderPdf(new OffersPdf(variables));
                });
    }

    public Mono<byte[]> generateAllValidatedOffersReport(String semesterFullName) {
        return internshipOfferService.getAllValidatedOffers(semesterFullName).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Offres de stages validées");
                    variables.put("session", SemesterUtil.convertInFrench(semesterFullName));
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

    public Mono<byte[]> generateStudentsNotEvaluatedReport(String semesterFullName) {
        return studentService.getAllWithNoEvaluationDateDuringSemester(semesterFullName).collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants qui n'ont pas encore été évalués par leur moniteur");
                    variables.put("session", SemesterUtil.convertInFrench(semesterFullName));
                    return pdfService.renderPdf(new StudentsPdf(variables));
                });
    }

}
