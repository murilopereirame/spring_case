package br.dev.murilopereira.spring_case.dto;

import java.util.Date;

public record TokenDTO(String token, Date validUntil) { }
