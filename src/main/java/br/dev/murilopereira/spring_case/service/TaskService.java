package br.dev.murilopereira.spring_case.service;

import br.dev.murilopereira.spring_case.dto.TaskDTO;
import br.dev.murilopereira.spring_case.model.SubTask;
import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.repository.SubTaskRepository;
import br.dev.murilopereira.spring_case.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    public Boolean isTaskDone(String taskId, String userId) {
        Iterable<SubTask> subTasks = subTaskRepository.findAllByTaskId(userId, taskId);
        boolean isDone = StreamSupport.stream(subTasks.spliterator(), false).allMatch(SubTask::isDone);

        taskRepository.updateTaskById(taskId, isDone);

        return isDone;
    }
}
