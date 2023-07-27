package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.dto.SuccessResponseDTO;
import br.dev.murilopereira.spring_case.dto.TaskDTO;
import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping(path="/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    Logger logger = LoggerFactory.getLogger(TaskController.class);
    @PostMapping(path="/new")
    public @ResponseBody ResponseEntity<?> createTask(@RequestBody TaskDTO taskDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Task task = new Task();
        task.setDone(taskDto.done());
        task.setTitle(taskDto.title());
        task.setUsers_iduser(userDetails.getUserUUID());

        task = taskRepository.save(task);
        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("Task created with success", task, new ArrayList<>()),
                new HttpHeaders(),
                200
        );
    }

    @GetMapping(path="/list")
    public @ResponseBody ResponseEntity<?> listTasks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Iterable<Task> taskList = taskRepository.findAllByUserId(userDetails.getUserUUID());

        return new ResponseEntity<SuccessResponseDTO>(
                new SuccessResponseDTO("Task listed with success", taskList, new ArrayList<>()),
                new HttpHeaders(),
                200
        );
    }
}
