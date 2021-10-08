package com.team4.backend.util;

import com.team4.backend.exception.InvalidPageRequestException;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.Min;

@Data
public class ValidatingPageRequest {

    Integer page;

    Integer size;

    public ValidatingPageRequest(@Min(0) Integer page, @Min(1) Integer size) throws InvalidPageRequestException {
        if(page < 0) {
            throw new InvalidPageRequestException("Page must be greater or equal to 0");
        }
        if(size < 1) {
            throw new InvalidPageRequestException("Size must be greater or equal to 1");
        }

        this.page = page;
        this.size = size;
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(page, size);
    }

    public Integer getOffset() {
        return page * size;
    }

}
