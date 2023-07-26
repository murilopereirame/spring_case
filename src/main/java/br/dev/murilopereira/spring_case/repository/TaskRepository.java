package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {
}
