package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.dto.SubTaskDTO;
import br.dev.murilopereira.spring_case.dto.SuccessResponseDTO;
import br.dev.murilopereira.spring_case.model.SubTask;
import br.dev.murilopereira.spring_case.repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping(path="/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskRepository subTaskRepository;

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
        Iterable<SubTask> subtaskList = subTaskRepository.findAllByTaskId(userDetails.getUserUUID(), taskId);
        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("SubTasks listed with success", subtaskList, new ArrayList<>()),
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
        subTaskRepository.updateSubTaskByTaskIdAndId(
                userDetails.getUserUUID(),
                taskId,
                subtaskId,
                subTaskDTO.content(),
                subTaskDTO.done()
        );

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
        subTaskRepository.deleteSubTaskByTaskIdAndId(
                userDetails.getUserUUID(),
                taskId,
                subtaskId
        );

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
