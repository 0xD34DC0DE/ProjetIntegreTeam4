package com.team4.backend.util;

import com.team4.backend.exception.InvalidPageRequestException;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log
public class ValidatingPageRequestTest {

    @Test
    void shouldCreatePageRequest() {
        //ARRANGE
        Integer page = 0;
        Integer size = 1;

        //ACT
        assertDoesNotThrow(() -> {
            ValidatingPageRequest pageRequest = new ValidatingPageRequest(page, size);
            assertNotNull(pageRequest.getPageRequest());
        }); // ASSERT
    }

    @Test
    void shouldNotCreatePageRequestInvalidPage() {
        //ARRANGE
        Integer page = -1;
        Integer size = 1;

        //ACT
        assertThrows(InvalidPageRequestException.class, () -> {
            ValidatingPageRequest pageRequest = new ValidatingPageRequest(page, size);
            assertNotNull(pageRequest.getPageRequest());
        });// ASSERT
    }

    @Test
    void shouldNotCreatePageRequestInvalidSize() {
        //ARRANGE
        Integer page = 0;
        Integer size = 0;

        //ACT
        assertThrows(InvalidPageRequestException.class, () -> {
            ValidatingPageRequest pageRequest = new ValidatingPageRequest(page, size);
            assertNotNull(pageRequest.getPageRequest());
        });// ASSERT
    }

    @Test
    void pageRequestOffsetValid() throws InvalidPageRequestException {
        //ARRANGE
        Integer page = 1;
        Integer size = 1;

        //ACT
        ValidatingPageRequest pageRequest = new ValidatingPageRequest(page, size);

        // ASSERT
        assertNotNull(pageRequest.getOffset());
    }

    @Test
    void shouldNotCreatePageRequestNullSize() {
        //ARRANGE
        Integer page = 0;
        Integer size = null;

        //ACT
        assertThrows(InvalidPageRequestException.class, () -> new ValidatingPageRequest(page, size));// ASSERT
    }

    @Test
    void shouldNotCreatePageRequestNullPage() {
        //ARRANGE
        Integer page = null;
        Integer size = 1;

        //ACT
        assertThrows(InvalidPageRequestException.class, () -> new ValidatingPageRequest(page, size));// ASSERT
    }

}
