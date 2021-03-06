package com.team4.backend.service;

import com.team4.backend.pdf.OffersPdf;
import com.team4.backend.pdf.StudentsPdfTemplate;
import com.team4.backend.util.SemesterUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Service
public class ReportService {

    private final InternshipOfferService internshipOfferService;

    private final StudentService studentService;

    private final SupervisorService supervisorService;

    private final PdfService pdfService;

    public ReportService(InternshipOfferService internshipOfferService, StudentService studentService, SupervisorService supervisorService, PdfService pdfService) {
        this.internshipOfferService = internshipOfferService;
        this.studentService = studentService;
        this.supervisorService = supervisorService;
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
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsNoCvReport() {
        return studentService.getAllStudentsWithNoCv().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants sans CV");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsUnvalidatedCvReport() {
        return studentService.getAllStudentsWithUnvalidatedCv().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants dont le CV n'est pas validé");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsNoInterviewReport() {
        return studentService.getStudentsNoInterview().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants n'ayant aucune covocation à une entrevue");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsWaitingInterviewReport() {
        return studentService.getStudentsWaitingInterview().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants en attente d'entrevue");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsWaitingInterviewResponseReport() {
        return studentService.getStudentsWaitingResponse().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants en attente d'une réponse d'entrevue");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsWithInternshipReport() {
        return studentService.getStudentsWithInternship().collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants qui ont trouvé un stage");
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
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
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateStudentsWithSupervisorWithNoCompanyEvaluation(String semesterFullName) {
        return supervisorService.getStudentsEmailWithSupervisorWithNoEvaluation(semesterFullName)
                .flatMapMany(studentsEmail -> Flux.fromIterable(studentsEmail).flatMap(studentService::findByEmail)).collectList()
                .flatMap(students -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("studentsList", students);
                    variables.put("date", LocalDate.now());
                    variables.put("title", "Étudiants dont le superviseur n'a pas encore évalué l'entreprise");
                    variables.put("session", SemesterUtil.convertInFrench(semesterFullName));
                    return pdfService.renderPdf(new StudentsPdfTemplate(variables));
                });
    }

}
