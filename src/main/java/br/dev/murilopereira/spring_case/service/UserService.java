package br.dev.murilopereira.spring_case.service;

import br.dev.murilopereira.spring_case.dto.CustomUserDetails;
import br.dev.murilopereira.spring_case.model.User;
import br.dev.murilopereira.spring_case.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User loadUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new Exception("USER_NOT_FOUND");
        }

        return user;
    }

    public CustomUserDetails loadUserByEmailAsUserDetails(String email) {
        User user = userRepository.findByEmail(email);
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(
                user,
                true,
                true,
                true,
                true,
                authList
        );
    }

    public User authenticate(String email, String password) throws Exception {
        User user = this.loadUserByEmail(email);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("INVALID_PASSWORD");
        }

        return user;
    }
}
