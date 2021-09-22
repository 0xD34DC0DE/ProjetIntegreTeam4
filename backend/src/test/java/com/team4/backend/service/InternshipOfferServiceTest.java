package com.team4.backend.service;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.repository.InternshipOfferRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Log
@ExtendWith(MockitoExtension.class)
public class InternshipOfferServiceTest {

    @Mock
    InternshipOfferRepository internshipOfferRepository;

    @InjectMocks
    InternshipOfferService internshipOfferService;

    @Test
    void addAnInternshipOffer(){
        //ARRANGE


        //ACT

        //ASSERT

    }
}
