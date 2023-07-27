package br.dev.murilopereira.spring_case.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idtask;

    @Column(name="title", length = 120)
    private String title;

    @Column(name = "done")
    private boolean done;

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="users_iduser", insertable = false, updatable = false)
    private User user;

    @Column(name = "users_iduser")
    private String users_iduser;

    @OneToMany(mappedBy = "task")
    private Set<SubTask> subtasks;

    public String getUuid() {
        return idtask;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUsers_iduser() {
        return users_iduser;
    }

    public void setUsers_iduser(String users_iduser) {
        this.users_iduser = users_iduser;
    }
}
