package com.unicomer.configuration.web;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ApiErrorWrapper DTO.
 */
@Getter
public class ApiErrorWrapper {

    private final List<ApiError> errors = new ArrayList<>();

    /**
     * Add an API error on the list.
     * 
     * @param error DTO Object
     */
    public final void addApiError(final ApiError error) {
        errors.add(error);
    }

    /**
     * Add an API error on the list.
     * 
     * @param type the error type
     * @param message the error global message
     * @param source the origin of the error
     * @param description the description detail message
     */
    public final void addFieldError(final String type, final String message, final String source,
            final String description) {
        final ApiError error = ApiError
                                .builder()
                                    .code(HttpStatus.BAD_REQUEST.value())
                                    .type(type)
                                    .message(message)
                                    .description(description)
                                    .source(source)
                                .build();
        errors.add(error);
    }

}
