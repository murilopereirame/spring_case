package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.SubTask;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SubTaskRepository extends CrudRepository<SubTask, UUID> {
}
