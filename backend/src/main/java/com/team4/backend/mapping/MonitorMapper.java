package com.team4.backend.mapping;

import com.team4.backend.model.Monitor;

public abstract class MonitorMapper {

    public static Monitor toEntity() {
        //TODO implementation
        return new Monitor();
    }

    /*
    TODO
        public static MonitorDto toDto(Monitor monitor) {
            return new MonitorDto();
        }
    */
}
