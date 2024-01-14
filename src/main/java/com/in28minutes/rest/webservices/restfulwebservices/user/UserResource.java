package com.in28minutes.rest.webservices.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
public class UserResource {
    UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service=service;
    }

    //Get /users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    //Get /users/1
    @GetMapping("/users/{id}")
    public User retrieveUsers(@PathVariable int id) {

        User user=service.findOne(id);
        if(user==null) {
            throw new UserNotFoundException("id"+id);
        }
        return user;

    }

    //POST /users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //Get /users/1
    @DeleteMapping ("/users/{id}")
    public void deleteUsers(@PathVariable int id) {
        service.deleteById(id);
    }


}
