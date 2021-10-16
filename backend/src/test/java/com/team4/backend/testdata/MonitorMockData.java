package com.team4.backend.testdata;

import com.team4.backend.dto.MonitorCreationDto;
import com.team4.backend.model.Monitor;

public class MonitorMockData {

    public static Monitor getMockMonitor() {
        return Monitor.monitorBuilder()
                .id("6151f7ac87d8fbea963710fd")
                .email("9182738492@gmail.com")
                .password("lao@dkv23")
                .firstName("Christopher")
                .lastName("Columbus")
                .companyName("CAE")
                .phoneNumber("514-882-2134")
                .registrationDate(null) // Current date
                .build();
    }

    public static MonitorCreationDto getMockMonitorDto() {
        return MonitorCreationDto.builder()
                .id("6151f7ac87d8fbea963710fd")
                .email("9182738492@gmail.com")
                .password("lao@dkv23")
                .firstName("Christopher")
                .lastName("Columbus")
                .companyName("CAE")
                .phoneNumber("514-882-2134")
                .registrationDate(null) // Current date
                .build();
    }

}