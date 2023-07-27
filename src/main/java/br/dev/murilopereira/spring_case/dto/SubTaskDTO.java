package br.dev.murilopereira.spring_case.dto;

import org.springframework.lang.Nullable;

public record SubTaskDTO(
        @Nullable String idsubtask,
        String content,
        boolean done,
        @Nullable String tasks_idtask,
        @Nullable String users_iduser
) { }
