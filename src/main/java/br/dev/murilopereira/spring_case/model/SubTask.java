package br.dev.murilopereira.spring_case.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="subtasks")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idsubtask;
    private String content;
    private boolean done;

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="users_iduser", insertable = false, updatable = false)
    private User user;

    @Column(name = "users_iduser")
    private String users_iduser;

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Task.class)
    @JoinColumn(name="tasks_idtask", insertable = false, updatable = false)
    private Task task;

    @Column(name = "tasks_idtask")
    private String tasks_idtask;

    public String getUuid() {
        return idsubtask;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsers_iduser() {
        return users_iduser;
    }

    public void setUsers_iduser(String users_iduser) {
        this.users_iduser = users_iduser;
    }

    public String getTasks_idtask() {
        return tasks_idtask;
    }

    public void setTasks_idtask(String tasks_idtask) {
        this.tasks_idtask = tasks_idtask;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
