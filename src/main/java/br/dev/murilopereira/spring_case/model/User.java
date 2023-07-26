package br.dev.murilopereira.spring_case.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class User{
   @Id
   @GeneratedValue(strategy= GenerationType.UUID)
    private String iduser;

   @Email
   @NotNull(message = "You have to provide a valid email")
   @Column(unique = true)
   private String email;

   @NotNull(message = "You have to provide a password")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private String password;

   @OneToMany(mappedBy="user")
   private Set<Task> tasks;

   @OneToMany(mappedBy="user")
   private Set<SubTask> subTasks;

   public String getUuid() {
       return iduser;
   }

   public String getEmail() {
       return email;
   }

   public void setEmail(String email) {
       this.email = email;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   @JsonIgnore
   public String getPassword() {
       return password;
   }
}