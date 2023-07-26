package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.model.SubTask;
import br.dev.murilopereira.spring_case.repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping(path="/subtasks")
public class SubTaskController {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @PostMapping(path="/create")
    public @ResponseBody SubTask createSubTask(
            @RequestParam(name="content") String content,
            @RequestParam(name="done", required = false, defaultValue = "false") boolean done,
            @RequestParam(name="task_id") String task_id
    ) {
        SubTask subtask = new SubTask();
        subtask.setContent(content);
        subtask.setDone(done);
        subtask.setTasks_idtask(task_id);

        return subTaskRepository.save(subtask);
    }
}
