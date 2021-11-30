package com.team4.backend.mapping;

import com.team4.backend.dto.*;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.testdata.InternshipOfferMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternshipOfferMapperTest {

    @Test
    void mapInternshipCreationDtoToEntity() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDto = InternshipOfferMockData.getInternshipOfferCreationDto();

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
        assertEquals(internshipOfferDto.getMonitorEmail(), internshipOffer.getMonitorEmail());
    }

    @Test
    void mapInternshipOfferEntityToInternshipOfferStudentInterestViewDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferStudentInterestViewDto internshipOfferStudentInterestViewDto = InternshipOfferMapper.toStudentInterestViewDto(internshipOffer);

        //ASSERT
        assertEquals(internshipOffer.getId(), internshipOfferStudentInterestViewDto.getId());
        assertEquals(internshipOffer.getCompanyName(), internshipOfferStudentInterestViewDto.getCompanyName());
        assertEquals(internshipOffer.getDescription(), internshipOfferStudentInterestViewDto.getDescription());
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
    void mapInternshipOfferEntityToInternshipManagerViewDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferInternshipManagerViewDto internshipOfferInternshipManagerViewDto = InternshipOfferMapper.toInternshipManagerViewDto(internshipOffer);

        //ASSERT
        assertEquals(internshipOffer.getId(), internshipOfferInternshipManagerViewDto.getId());
        assertEquals(internshipOffer.getIsExclusive(), internshipOfferInternshipManagerViewDto.getIsExclusive());
        assertEquals(internshipOffer.getLimitDateToApply(), internshipOfferInternshipManagerViewDto.getLimitDateToApply());
        assertEquals(internshipOffer.getBeginningDate(), internshipOfferInternshipManagerViewDto.getBeginningDate());
        assertEquals(internshipOffer.getEndingDate(), internshipOfferInternshipManagerViewDto.getEndingDate());
        assertEquals(internshipOffer.getCompanyName(), internshipOfferInternshipManagerViewDto.getCompanyName());
        assertEquals(internshipOffer.getDescription(), internshipOfferInternshipManagerViewDto.getDescription());
        assertEquals(internshipOffer.getMinSalary(), internshipOfferInternshipManagerViewDto.getMinSalary());
        assertEquals(internshipOffer.getMaxSalary(), internshipOfferInternshipManagerViewDto.getMaxSalary());
    }

    @Test
    void mapInternshipOfferEntityToInternshipOfferMonitorViewDto() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        //ACT
        InternshipOfferMonitorViewDto internshipOfferMonitorViewDto =
                InternshipOfferMapper.toMonitorViewDto(internshipOffer);

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
