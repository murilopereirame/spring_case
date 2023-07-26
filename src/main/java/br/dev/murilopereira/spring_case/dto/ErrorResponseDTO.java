package br.dev.murilopereira.spring_case.dto;

import java.util.ArrayList;

public record ErrorResponseDTO(String message, String code, ArrayList<String> details) {};
