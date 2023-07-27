package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping(path="/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    Logger logger = LoggerFactory.getLogger(TaskController.class);
    @PostMapping(path="/create")
    public @ResponseBody Task createTask(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "done", defaultValue = "false", required = false) boolean done,
            @RequestParam(name = "user_id") String user_id
            ) {
        Task task = new Task();
        task.setDone(done);
        task.setTitle(title);
        task.setUsers_iduser(user_id);

        return taskRepository.save(task);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Task> listTasks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        logger.info(userDetails.getUserUUID());

        return taskRepository.findAll();
    }
}
