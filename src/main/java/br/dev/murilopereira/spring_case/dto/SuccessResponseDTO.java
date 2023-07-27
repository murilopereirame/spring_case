package br.dev.murilopereira.spring_case.dto;

import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.Collections;

public record SuccessResponseDTO(String message, Object data, ArrayList<String> details ) {};

