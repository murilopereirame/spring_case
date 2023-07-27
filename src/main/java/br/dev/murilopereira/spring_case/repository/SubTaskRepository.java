package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.SubTask;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SubTaskRepository extends CrudRepository<SubTask, String> {
    @Query(value = "SELECT * FROM subtasks WHERE users_iduser= ?1 AND tasks_idtask = ?2", nativeQuery = true)
    Iterable<SubTask> findAllByTaskId(String users_iduser, String tasks_idtask);

    @Modifying
    @Transactional
    @Query(value = "UPDATE SubTask st SET st.content = ?4, st.done = ?5 WHERE st.users_iduser = ?1 AND st.tasks_idtask = ?2 AND st.idsubtask = ?3")
    Integer updateSubTaskByTaskIdAndId(String users_iduser, String tasks_idtask, String subtaskId, String content, boolean done);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM SubTask st WHERE st.users_iduser = ?1 AND st.tasks_idtask = ?2 AND st.idsubtask = ?3")
    void deleteSubTaskByTaskIdAndId(String users_iduser, String tasks_idtask, String subtaskId);
}
