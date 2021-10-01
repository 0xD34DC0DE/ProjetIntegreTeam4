package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.testdata.InternshipOfferMockData;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternshipOfferMapperTest {

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        InternshipOfferDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferDto();

        //ACT
        InternshipOffer internshipOffer = InternshipOfferMapper.toEntity(internshipOfferDto);

        //ASSERT
        assertEquals(internshipOfferDto.getLimitDateToApply(), internshipOffer.getLimitDateToApply());
        assertEquals(internshipOfferDto.getBeginningDate(), internshipOffer.getBeginningDate());
        assertEquals(internshipOfferDto.getEndingDate(), internshipOffer.getEndingDate());
        assertEquals(internshipOfferDto.getCompanyName(), internshipOffer.getCompanyName());
        assertEquals(internshipOfferDto.getDescription(), internshipOffer.getDescription());
        assertEquals(internshipOfferDto.getMinSalary(), internshipOffer.getMinSalary());
        assertEquals(internshipOfferDto.getMaxSalary(), internshipOffer.getMaxSalary());
        assertEquals(internshipOfferDto.isValidated(), internshipOffer.isValidated());
        assertEquals(internshipOfferDto.getEmailOfMonitor(),internshipOffer.getEmailOfMonitor());

    }

    @Test
    void mapEntityToDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferDto internshipOfferDto = InternshipOfferMapper.toDto(internshipOffer);

        //ASSERT
        assertEquals(internshipOffer.getId(), internshipOfferDto.getId());
        assertEquals(internshipOffer.getLimitDateToApply(), internshipOfferDto.getLimitDateToApply());
        assertEquals(internshipOffer.getBeginningDate(), internshipOfferDto.getBeginningDate());
        assertEquals(internshipOffer.getEndingDate(), internshipOfferDto.getEndingDate());
        assertEquals(internshipOffer.getCompanyName(), internshipOfferDto.getCompanyName());
        assertEquals(internshipOffer.getDescription(), internshipOfferDto.getDescription());
        assertEquals(internshipOffer.getMinSalary(), internshipOfferDto.getMinSalary());
        assertEquals(internshipOffer.getMaxSalary(), internshipOfferDto.getMaxSalary());
        assertEquals(internshipOffer.isValidated(), internshipOfferDto.isValidated());


        assertEquals(InternshipOfferMockData.getInterestedStudentsEmailList(),
                internshipOfferDto.getListEmailInterestedStudents());
    }
}
