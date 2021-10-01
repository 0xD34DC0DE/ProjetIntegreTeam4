package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.repository.InternshipOfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    private final MonitorService monitorService;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository, MonitorService monitorService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.monitorService = monitorService;
    }

    public Mono<InternshipOfferDto> addAnInternshipOffer(InternshipOfferDto internshipOfferDTO) {
        return monitorService.findMonitorByEmail(internshipOfferDTO.getEmailOfMonitor())
                .flatMap(monitor -> internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO, monitor)))
                .map(InternshipOfferMapper::toDto);
    }

    public Flux<InternshipOfferDto> getNonValidatedInternshipOffers() {
        return internshipOfferRepository.findAllInternshipOfferByIsValidatedFalse().map(InternshipOfferMapper::toDto);
    }

    public Mono<InternshipOfferDto> validateInternshipOffer(@RequestParam String id){
        return internshipOfferRepository.findById(id).map(offer -> {offer.setValidated(true);
            return offer;
        }).flatMap(internshipOfferRepository::save)
                .map(InternshipOfferMapper::toDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't find InternshipOffer with this id.")));
    }
}
