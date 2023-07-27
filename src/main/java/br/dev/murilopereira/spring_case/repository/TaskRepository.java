package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, String> {
    @Query(value = "SELECT * FROM tasks WHERE users_iduser= ?1", nativeQuery = true)
    Iterable<Task> findAllByUserId(String users_iduser);
}
