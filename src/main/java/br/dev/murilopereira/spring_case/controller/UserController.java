package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.model.User;
import br.dev.murilopereira.spring_case.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path="/register", consumes = "application/json", produces = "application/json")
    public @ResponseBody User registerNewUser(@Valid @RequestBody User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));

       return userRepository.save(user);
    }
}
