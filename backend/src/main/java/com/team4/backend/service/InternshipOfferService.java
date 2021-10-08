package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.exception.UserDoNotExistException;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.repository.InternshipOfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    private final MonitorService monitorService;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository, MonitorService monitorService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.monitorService = monitorService;
    }

    public Mono<InternshipOffer> addAnInternshipOffer(InternshipOfferDto internshipOfferDTO) {
        return monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())
                .flatMap(exist -> exist ?
                        internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO))
                        : Mono.error(new UserDoNotExistException("Can't find monitor!")));
    }

    public Flux<InternshipOffer> getNonValidatedInternshipOffers() {
        return internshipOfferRepository.findAllInternshipOfferByIsValidatedFalse();
    }
    public Flux<InternshipOffer> getNotYetValidatedInternshipOffers() {
        return internshipOfferRepository.findAllByValidationDateNullAndIsValidatedFalse();
    }

    public Mono<InternshipOffer> validateInternshipOffer(String id){
        return internshipOfferRepository.findById(id).map(offer -> {
            offer.setValidated(true);
            offer.setValidationDate(LocalDateTime.now());
            return offer;
        }).flatMap(internshipOfferRepository::save)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't find InternshipOffer with this id.")));
    }

    public Mono<InternshipOffer> refuseInternshipOffer(String id){
        return internshipOfferRepository.findById(id).map(offer -> {
            offer.setValidated(false);
            offer.setValidationDate(LocalDateTime.now());
            return offer;
        }).flatMap(internshipOfferRepository::save)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't find InternshipOffer with this id.")));
    }
}
