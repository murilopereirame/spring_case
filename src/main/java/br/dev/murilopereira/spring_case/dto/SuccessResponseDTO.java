package br.dev.murilopereira.spring_case.dto;

import java.util.List;

public record SuccessResponseDTO(String message, Object data, List<?> details, String status) {
    public SuccessResponseDTO(String message, Object data, List<?> details) {
        this(message, data, details, "SUCCESS");
    }
};

