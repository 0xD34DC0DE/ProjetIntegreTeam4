package com.team4.backend.service;

import com.team4.backend.repository.InternshipContractRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InternshipContractServiceTest {

    @Mock
    InternshipContractRepository internshipContractRepository;

    @InjectMocks
    InternshipContractService internshipContractService;

}
