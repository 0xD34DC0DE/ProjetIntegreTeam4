package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.mapping.InternshipOfferMapper;
import com.team4.backend.repository.InternshipOfferRepository;
import org.springframework.stereotype.Service;
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
        return monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())
                .flatMap(monitor -> internshipOfferRepository.save(InternshipOfferMapper.toEntity(internshipOfferDTO)))
                .map(InternshipOfferMapper::toDto);
    }

}
