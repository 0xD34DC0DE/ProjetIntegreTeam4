package com.team4.backend.service;

import com.team4.backend.repository.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {

    @Mock
    SemesterRepository semesterRepository;

    @InjectMocks
    SemesterService semesterService;

    @Test
    void shouldCreateSemester() {
        //ARRANGE

        //ACT

        //ASSERT
        
    }
}
