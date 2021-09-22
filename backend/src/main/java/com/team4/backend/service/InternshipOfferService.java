package com.team4.backend.service;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.repository.InternshipOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;

    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository) {
        this.internshipOfferRepository = internshipOfferRepository;
    }

    /*
        TODO
            -> create a DTO
            ->create a constructor in document that takes dto as argument and map it
     */
    public void addAnInternshipOffer(){
    }
}
