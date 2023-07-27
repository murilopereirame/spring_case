package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, String> {
}
