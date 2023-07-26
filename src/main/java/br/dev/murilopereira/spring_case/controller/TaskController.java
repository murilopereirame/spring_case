package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.model.Task;
import br.dev.murilopereira.spring_case.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path="/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

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
    public @ResponseBody Iterable<Task> listTasks() {
        return taskRepository.findAll();
    }
}
