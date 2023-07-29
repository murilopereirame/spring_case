package br.dev.murilopereira.spring_case.controller;

import br.dev.murilopereira.spring_case.dto.*;
import br.dev.murilopereira.spring_case.model.User;
import br.dev.murilopereira.spring_case.repository.UserRepository;
import br.dev.murilopereira.spring_case.service.UserService;
import br.dev.murilopereira.spring_case.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(path="/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDTO user) {
        if(userService.doesUserExists(user.getEmail())) {
            return new ResponseEntity<>(new ErrorResponseDTO("User already registered", "USER_ALREADY_EXISTS", new ArrayList<>()), new HttpHeaders(), 400);
        }

        User usr = new User();
        usr.setEmail(user.getEmail());
        usr.setPassword(passwordEncoder.encode(user.getPassword()));

       return new ResponseEntity<User>(userRepository.save(usr), new HttpHeaders(), 201);
    }

    @PostMapping(value="/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {
        try {
            User user = userService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            final TokenDTO token = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.status(200).body(new SuccessResponseDTO("Success", token, new ArrayList<>()) );
        } catch (Exception e) {
            switch(e.getMessage()) {
                case "USER_NOT_FOUND", "INVALID_PASSWORD" -> {
                    return ResponseEntity.status(401).body(new ErrorResponseDTO("Username/Password invalid", "INVALID_CREDENTIALS", new ArrayList<>()));
                }
            }

            logger.error(e.getMessage());
            return ResponseEntity.status(500).body(new ErrorResponseDTO("Server Error", "INTERNAL_SERVER_ERROR", new ArrayList<>()));
        }
    }
}
