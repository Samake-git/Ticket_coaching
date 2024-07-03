package com.example.ticketing.service;

import com.example.ticketing.model.User;
import com.example.ticketing.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Data
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "Deleted successfuly";
    }

    public User update(Long id,User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.findById(id)
                .map(p -> {
                    p.setUsername(user.getUsername());
                    p.setPassword(user.getPassword());
                    p.setRole(user.getRole());
                    p.setEmail(user.getEmail());
                    return userRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("Veuillez reessayer, s'il vous plait"));
    }


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
