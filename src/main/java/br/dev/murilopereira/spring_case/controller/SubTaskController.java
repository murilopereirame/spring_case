package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.dto.ErrorResponseDTO;
import br.dev.murilopereira.spring_case.dto.SubTaskDTO;
import br.dev.murilopereira.spring_case.dto.SuccessResponseDTO;
import br.dev.murilopereira.spring_case.model.SubTask;
import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.repository.SubTaskRepository;
import br.dev.murilopereira.spring_case.repository.TaskRepository;
import br.dev.murilopereira.spring_case.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@CrossOrigin("*")
@RequestMapping(path="/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @PostMapping(path="/new/{task_id}")
    public @ResponseBody ResponseEntity<?> createSubTask(
            @PathVariable(name="task_id") String taskId,
            @RequestBody SubTaskDTO subtaskDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        SubTask subtask = new SubTask();
        subtask.setContent(subtaskDto.content());
        subtask.setDone(subtaskDto.done());
        subtask.setTasks_idtask(taskId);
        subtask.setUsers_iduser(userDetails.getUserUUID());

        subtask = subTaskRepository.save(subtask);

        taskService.isTaskDone(taskId, userDetails.getUserUUID());

        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("SubTask created with success", subtask, new ArrayList<>()),
                new HttpHeaders(),
                201
        );
    }

    @GetMapping(path="/list/{task_id}")
    public @ResponseBody ResponseEntity<?> listSubTasksByTask(
            @PathVariable(value="task_id") String taskId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Optional<Task> task = taskRepository.findById(taskId);
        Iterable<SubTask> subtaskList = subTaskRepository.findAllByTaskId(userDetails.getUserUUID(), taskId);
        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("SubTasks listed with success", subtaskList, List.of(task)),
                new HttpHeaders(),
                200
        );
    }

    @PatchMapping(path="/update/{task_id}/{subtask_id}")
    public @ResponseBody ResponseEntity<?> updateSubTask(
            @PathVariable(value="subtask_id") String subtaskId,
            @PathVariable(value="task_id") String taskId,
            @RequestBody SubTaskDTO subTaskDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer updated = subTaskRepository.updateSubTaskByTaskIdAndId(
                userDetails.getUserUUID(),
                taskId,
                subtaskId,
                subTaskDTO.content(),
                subTaskDTO.done()
        );

        if(updated == 0) {
            return ResponseEntity
                    .status(404)
                    .body(
                            new ErrorResponseDTO(
                                    "SubTask Not Found",
                                    "NOT_FOUND",
                                    new ArrayList<>()
                            )
                    );
        }

        taskService.isTaskDone(taskId, userDetails.getUserUUID());

        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("SubTasks updated with success", Map.of("updated", true), new ArrayList<>()),
                new HttpHeaders(),
                200
        );
    }

    @DeleteMapping(path="/delete/{task_id}/{subtask_id}")
    public @ResponseBody ResponseEntity<?> deleteSubTask(
            @PathVariable(value="subtask_id") String subtaskId,
            @PathVariable(value="task_id") String taskId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer deleted = subTaskRepository.deleteSubTaskByTaskIdAndId(
                userDetails.getUserUUID(),
                taskId,
                subtaskId
        );

        if(deleted == 0) {
            return ResponseEntity
                    .status(404)
                    .body(
                            new ErrorResponseDTO(
                                    "SubTask Not Found",
                                    "NOT_FOUND",
                                    new ArrayList<>()
                            )
                    );
        }

        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO(
                        "SubTasks deleted with success",
                        Map.of("deleted", true),
                        new ArrayList<>()
                ),
                new HttpHeaders(),
                200
        );
    }
}
