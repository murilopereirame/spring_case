package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends CrudRepository<Task, String> {
    @Query(value = "SELECT * FROM tasks WHERE users_iduser= ?1 ORDER BY title", nativeQuery = true)
    Iterable<Task> findAllByUserId(String users_iduser);

    @Query(value = "SELECT * FROM tasks WHERE users_iduser= ?1 AND idtask=?2", nativeQuery = true)
    Task findByUserAndId(String users_iduser, String idtask);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tasks SET done=?2 WHERE idtask=?1", nativeQuery = true)
    Integer updateTaskById(String taskId, boolean done);
}
