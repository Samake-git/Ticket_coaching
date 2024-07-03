package com.example.ticketing.controller;

import com.example.ticketing.model.User;
import com.example.ticketing.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/admin/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/admin/Afficher/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id).orElse(null);
    }

    @PutMapping ("/admin/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping ("/admin/delete/{id}")
    public String delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
