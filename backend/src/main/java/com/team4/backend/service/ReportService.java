package com.team4.backend.service;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.pdf.NonValidatedOffersPdfTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
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

    public Mono<byte[]> generateAllNonValidatedOffersReport() {
        // TODO: implémenter les dates de session
        return internshipOfferService.getAllNonValidatedOffers(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE)).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    return pdfService.renderPdf(new NonValidatedOffersPdfTemplate(variables));
                });
    }

    public Mono<byte[]> generateAllValidatedOffersReport() {
        // TODO: implémenter les dates de session
        return internshipOfferService.getAllValidatedOffers(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE)).collectList()
                .flatMap(nonValidatedOffers -> {
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("internshipOfferList", nonValidatedOffers);
                    return pdfService.renderPdf(new NonValidatedOffersPdfTemplate(variables));
                });
    }

}
