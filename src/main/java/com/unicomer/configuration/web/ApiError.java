package com.unicomer.configuration.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ApiError DTO.
 */
@Getter
@Setter
@Builder
public class ApiError {

    private Integer code;

    private String type;

    @Builder.Default
    private String source = "base";

    private String message;
    
    private String description;

}
