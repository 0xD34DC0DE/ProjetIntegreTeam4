package com.team4.backend.util;

import com.team4.backend.exception.InvalidPageRequestException;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Data
public abstract class ValidatingPageRequest {

    public static PageRequest getPageRequest(Integer page, Integer size) throws InvalidPageRequestException {
        validateValues(page, size);
        return PageRequest.of(page, size);
    }

    public static PageRequest getPageRequest(Integer page, Integer size, Sort sort) throws InvalidPageRequestException {
        validateValues(page, size);
        return PageRequest.of(page, size, sort);
    }

    public static Mono<PageRequest> getPageRequestMono(Integer page, Integer size) {
        try {
            validateValues(page, size);
        } catch (InvalidPageRequestException e) {
            return Mono.error(e);
        }
        return Mono.just(PageRequest.of(page, size));
    }

    public static Mono<PageRequest> getPageRequestMono(Integer page, Integer size, Sort sort) {
        try {
            validateValues(page, size);
        } catch (InvalidPageRequestException e) {
            return Mono.error(e);
        }
        return Mono.just(PageRequest.of(page, size, sort));
    }

    public static <T> Flux<T> applyPaging(Collection<T> list, Integer page, Integer size) {
        return Mono.just(list).flatMapMany(ts -> {
            try {
                validateValues(page, size);
            } catch (InvalidPageRequestException e) {
                return Mono.error(e);
            }
            return Flux.fromIterable(ts).skip((long) page * size).take(size);
        });
    }

    private static void validateValues(Integer page, Integer size) throws InvalidPageRequestException {
        if (page == null || size == null) {
            throw new InvalidPageRequestException("Arguments of page request can't be null");
        }
        if (page < 0) {
            throw new InvalidPageRequestException("Page must be greater or equal to 0");
        }
        if (size < 1) {
            throw new InvalidPageRequestException("Size must be greater or equal to 1");
        }
    }

}
