package br.dev.murilopereira.spring_case.repository;

import br.dev.murilopereira.spring_case.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    User findByEmail(String email);
}
