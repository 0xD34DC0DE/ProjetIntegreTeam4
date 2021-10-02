package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferMonitorViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.testdata.InternshipOfferMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternshipOfferMapperTest {

    @Test
    void mapInternshipCreationDtoToEntity() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferDto();

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
        assertEquals(internshipOfferDto.getEmailOfMonitor(), internshipOffer.getEmailOfMonitor());
    }

    @Test
    void mapInternshipOfferEntityToInternshipOfferStudentViewDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferStudentViewDto internshipOfferStudentViewDto = InternshipOfferMapper.toStudentViewDto(internshipOffer);

        //ASSERT
        assertEquals(internshipOffer.getId(), internshipOfferStudentViewDto.getId());
        assertEquals(internshipOffer.getIsExclusive(), internshipOfferStudentViewDto.getIsExclusive());
        assertEquals(internshipOffer.getLimitDateToApply(), internshipOfferStudentViewDto.getLimitDateToApply());
        assertEquals(internshipOffer.getBeginningDate(), internshipOfferStudentViewDto.getBeginningDate());
        assertEquals(internshipOffer.getEndingDate(), internshipOfferStudentViewDto.getEndingDate());
        assertEquals(internshipOffer.getCompanyName(), internshipOfferStudentViewDto.getCompanyName());
        assertEquals(internshipOffer.getDescription(), internshipOfferStudentViewDto.getDescription());
        assertEquals(internshipOffer.getMinSalary(), internshipOfferStudentViewDto.getMinSalary());
        assertEquals(internshipOffer.getMaxSalary(), internshipOfferStudentViewDto.getMaxSalary());
    }

    @Test
    void mapInternshipOfferEntityToInternshipOfferMonitorViewDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferMonitorViewDto internshipOfferMonitorViewDto =
                InternshipOfferMapper.toMonitorView(internshipOffer);

        //ASSERT
        assertEquals(internshipOffer.getId(), internshipOfferMonitorViewDto.getId());
        assertEquals(internshipOffer.getLimitDateToApply(), internshipOfferMonitorViewDto.getLimitDateToApply());
        assertEquals(internshipOffer.getBeginningDate(), internshipOfferMonitorViewDto.getBeginningDate());
        assertEquals(internshipOffer.getEndingDate(), internshipOfferMonitorViewDto.getEndingDate());
        assertEquals(internshipOffer.getCompanyName(), internshipOfferMonitorViewDto.getCompanyName());
        assertEquals(internshipOffer.getDescription(), internshipOfferMonitorViewDto.getDescription());
        assertEquals(internshipOffer.getMinSalary(), internshipOfferMonitorViewDto.getMinSalary());
        assertEquals(internshipOffer.getMaxSalary(), internshipOfferMonitorViewDto.getMaxSalary());
        assertEquals(internshipOffer.getListEmailInterestedStudents(),
                internshipOfferMonitorViewDto.getListEmailInterestedStudents());
    }

}
