package br.dev.murilopereira.spring_case.dto;

import org.springframework.lang.Nullable;

public record TaskDTO(
    @Nullable String idtask,
    String title,
    boolean done,
    @Nullable String user_iduser
){}
