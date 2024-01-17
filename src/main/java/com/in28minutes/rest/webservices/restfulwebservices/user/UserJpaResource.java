package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {
    //UserDaoService service;

    UserRepository repository;

    PostRepository postRepository;

    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    //Get /users
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {

        //return service.findAll();
        return repository.findAll();
    }

    //Get /users/1
    @GetMapping("/jpa/users/{id}")
    public Optional<User> retrieveUsers(@PathVariable int id) {

        Optional<User> user=repository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("id"+id);
        }
        return user;


    }

    //POST /users
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = repository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {

        Optional<User> user=repository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("id"+id);
        }
        post.setUser(user.get());
        Post savePost= postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savePost.getId())
                .toUri();

        return ResponseEntity.created(location).build();


    }

    //Get /users/1
    @DeleteMapping ("/jpa/users/{id}")
    public void deleteUsers(@PathVariable int id) {

        repository.deleteById(id);
    }

    @GetMapping ("/jpa/users/{id}/posts")
    public List<Post> reterievePostsforUsers(@PathVariable int id) {
        Optional<User> user=repository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("id"+id);
        }
      return user.get().getPosts();

    }


}
